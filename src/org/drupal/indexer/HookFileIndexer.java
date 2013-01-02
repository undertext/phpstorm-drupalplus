package org.drupal.indexer;


import com.intellij.codeInsight.CodeInsightBundle;
import com.intellij.lang.StdLanguages;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiRecursiveElementVisitor;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.containers.FactoryMap;
import com.intellij.util.indexing.*;
import com.intellij.util.io.DataExternalizer;
import com.intellij.util.io.KeyDescriptor;
import com.jetbrains.php.lang.psi.PhpFile;
import com.jetbrains.php.lang.psi.elements.Function;
import org.drupal.models.Hook;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HookFileIndexer extends FileBasedIndexExtension<String, List<Hook>> {

    public static final ID<String, List<Hook>> INDEX_ID = ID.create("drupalhooks");

    @NotNull
    @Override
    public ID<String, List<Hook>> getName() {
        return INDEX_ID;
    }

    @NotNull
    @Override
    public DataIndexer<String, List<Hook>, FileContent> getIndexer() {
        return new DataIndexer<String, List<Hook>, FileContent>() {
            @Override
            @NotNull
            public Map<String, List<Hook>> map(FileContent inputData) {
              final  Map<String, List<Hook>> map = new FactoryMap<String, List<Hook>>() {
                    @Override
                    protected List<Hook> create(String key) {
                        return new ArrayList<Hook>();
                    }
                };


              final PsiFile file= inputData.getPsiFile();
              if (file.getClass().getName().equals("com.jetbrains.php.lang.psi.PhpFile")){
                  PhpFile phpFile=(PhpFile)file;
                  System.out.println("FILE:"+file.getName());

                  phpFile.accept(new PsiRecursiveElementVisitor(){

                      public void visitElement(com.intellij.psi.PsiElement element) {
                      super.visitElement(element);
                      if (element instanceof Function){
                          Function phpFunction=(Function) element;
                          if (phpFunction.getFQN().contains("\\hook_")){
                              Hook impl = new Hook(phpFunction.getFQN().substring(1,phpFunction.getFQN().length()));
                              map.get(file.getName()).add(impl);
                          }
                      }

                      }

                  });
              }
                return map;
            }
        };
    }

    @Override
    public KeyDescriptor<String> getKeyDescriptor() {
        return new KeyDescriptor<String>() {
            @Override
            public int getHashCode(String value) {
                return value.hashCode();
            }

            @Override
            public boolean isEqual(String val1, String val2) {
                return val1.equals(val2);
            }

            @Override
            public void save(DataOutput out, String value) throws IOException {
                System.out.println(value);
                out.writeUTF(value);
            }

            @Override
            public String read(DataInput in) throws IOException {
                return in.readUTF();
            }
        };
    }

    @Override
    public DataExternalizer<List<Hook>> getValueExternalizer() {
        return new DataExternalizer<List<Hook>>() {
            @Override
            public void save(DataOutput out, List<Hook> value) throws IOException {
                out.writeInt(value.size());
                for (Hook info : value) {
                    out.writeUTF(info.getName());
                }
            }

            @Override
            public List<Hook> read(DataInput in) throws IOException {
                int size = in.readInt();
                ArrayList<Hook> infos = new ArrayList<Hook>(size);
                for (int i = 0; i < size; i++) {
                    infos.add(new Hook(in.readUTF()));
                }
                return infos;
            }
        };
    }

    @Override
    public FileBasedIndex.InputFilter getInputFilter() {
        return new FileBasedIndex.InputFilter() {
            @Override
            public boolean acceptInput(VirtualFile file) {
                String fileName=file.getName();
                if (fileName.contains(".")){
                String ext=fileName.substring(fileName.indexOf("."),fileName.length() ) ;
                if (ext.equals(".api.php")) return true;
                }
                return false;
            }
        };
    }

    @Override
    public boolean dependsOnFileContent() {
        return true;
    }



    @Override
    public int getVersion() {
        return 1;
    }
}

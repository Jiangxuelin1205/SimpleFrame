package ClazzLoader;

import java.io.*;

public class MyClassLoader extends ClassLoader {

    private String path;

    public MyClassLoader(String path) {
        this.path = path;
    }

    private byte[] loadByte(String name) throws IOException {
        String filePath = path + name + ".class";
        File file = new File(filePath);

        InputStream in = new FileInputStream(file);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            int i;
            while ((i = in.read()) != -1) {
                out.write(i);
            }
        } finally {
            in.close();
            out.close();
        }
        return out.toByteArray();
    }

    @Override
    public Class<?> findClass(String name) {
        try {
            byte[] classByte = loadByte(name);
            return defineClass(name, classByte, 0, classByte.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package c230911.dataStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class DataStreamTest {
	public static void test() {
		try {
			String path = DataStreamTest.class.getResource("").getPath() + "test.txt";
			FileOutputStream fos = new FileOutputStream(path);
			DataOutputStream dos = new DataOutputStream(fos);
			
			dos.writeInt(1);
			dos.writeUTF("아무개1");
			dos.writeDouble(90.0);
			
			dos.writeInt(2);
			dos.writeUTF("아무개2");
			dos.writeDouble(93.0);
			
			dos.writeInt(3);
			dos.writeUTF("아무개3");
			dos.writeDouble(100.0);
			
			dos.flush();
			dos.close();
			fos.close();
			
			FileInputStream fis = new FileInputStream(path);
			DataInputStream dis = new DataInputStream(fis);
			for(int i=0; i<3; i++) {
				int num = dis.readInt();
				String name = dis.readUTF();
				double score = dis.readDouble();
				
				System.out.println(num+". "+name+" : "+score+"점");
			}
			dis.close();
			fis.close();
		}catch(Exception e) {
		}
	}
}
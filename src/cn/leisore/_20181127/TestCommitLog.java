package cn.leisore._20181127;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TestCommitLog {

	public static void main(String[] args) throws Exception {

		DataInputStream di = new DataInputStream(new FileInputStream("E:/production/DXP/榆林Bugs/store/commitlog/00000000000000000000"));
		int size = di.readInt();
		int magic = di.readInt();
		
		System.out.println(size);
		System.out.println(magic);
		
		 int MESSAGE_MAGIC_CODE = 0xAABBCCDD ^ 1880681586 + 8;
		 int BLANK_MAGIC_CODE = 0xBBCCDDEE ^ 1880681586 + 8;
		 
		 System.out.println(MESSAGE_MAGIC_CODE);
			System.out.println(BLANK_MAGIC_CODE);
	}

}

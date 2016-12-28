package compress;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;

import compress.HuffmanTree.Node;

public class Decompress {

	private String decompressFileName;
	private BufferedWriter br;
	private String maintainString = "";
	private HuffmanTree<Character> huffmanTree;
	private Node<Character> headNode;
	private BufferedOutputStream bos;
	private String tragetFile;
	private BufferedInputStream dataStream;
	
//	public static void main(String[] args) {
//		Decompress decompress = new Decompress("236.txt", "123.dat");
//	}
	
	public Decompress(String tragetFile, String decompressFileName) {
		this.tragetFile = tragetFile;
		this.decompressFileName = decompressFileName;
		
		try {
			
			
			 dataStream = 
					new BufferedInputStream(new FileInputStream(decompressFileName));
			   
				loadHuffmanTree();
				startDecompress();
				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadHuffmanTree() {// ���ļ��л�ȡhaffman��
		try {
			System.out.println(decompressFileName);
			int suffixNameIndex = decompressFileName.lastIndexOf('\\');
			
			String huffman = decompressFileName.substring(0, suffixNameIndex + 1) + "huffman.dat";
			
			ObjectInputStream loadHfmTStream = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(huffman)));
			huffmanTree = (HuffmanTree<Character>)loadHfmTStream.readObject();
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public void startDecompress() {// ��byte��ת��ΪString��ʽ
		File fileDecompress = new File(decompressFileName);
		long fileLength = fileDecompress.length();
		int tailbyte = (int) (fileLength % 1000);
		byte[] contentByte = new byte[1000];
		
		byte[] tailContenByte = new byte[tailbyte];
		try {

			bos = new BufferedOutputStream(new FileOutputStream(tragetFile,true));
			br = new BufferedWriter(new OutputStreamWriter(bos, "GBK"));
			
				for (int i = 0; i < fileLength / 1000; i++) {//�ִζ�ȡ��С�ڴ�ѹ��
				
					dataStream.read(contentByte);
					
					decode(bytesToHexString(contentByte));
				}
				
				dataStream.read(tailContenByte);//��ȡ����1000������
				decode(bytesToHexString(tailContenByte));

				br.close();
				bos.close();// �ر�д���ļ���

			
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("��ѹ���");
	}

	public String bytesToHexString(byte[] src) {// ��byte������ת��Ϊ�������ַ���

		StringBuilder stringBuilder = new StringBuilder();
		String shortByte;//Ϊ���ǰ����Ķ̶������ַ���
		int byteLength = 0;
		
		for (int i = 0; i < src.length; i++) {
			
			shortByte = Integer.toBinaryString(src[i]).substring(0);//��byteת��Ϊ�������ַ���
			byteLength = 7 - shortByte.length();//��ǰbyte�ĳ���

			if (shortByte.length() != 7) {
				for (int j = 0; j < byteLength; j++) {
					shortByte = "0" + shortByte;
				}
			}
			
			stringBuilder = stringBuilder.append(shortByte);
		}
		return stringBuilder.toString();
	}

	public void decode(String byteString) {// ���뽫�������ַ�������


		StringBuffer binaryStringBuffer = new StringBuffer();
		byteString = maintainString + byteString;//����һ�ν�ѹ��ʣ������������ĩβ
		
		String subString = "";

		headNode = huffmanTree.getHead();
		Node<Character> currentNode = headNode;

		for (int i = 0; i < byteString.length(); i++) {
			currentNode = headNode;

			for (int j = i; j < byteString.length(); j++) {

				if (currentNode.Lchild == null && currentNode.Rchild == null) {
                    System.out.print(currentNode.data);
					binaryStringBuffer.append(currentNode.data);
					i = j - 1;
					break;
				}

				if (byteString.charAt(j) == '1') {
					
					currentNode = currentNode.Lchild;
				} else {
	
					currentNode = currentNode.Rchild;
				}
			}
		}
		
		if (currentNode.Lchild != null || currentNode.Rchild != null) {
			maintainString = subString;//����ǰ�ε�������ݱ���
		}
		
		SaveUncompressFile(binaryStringBuffer.toString());// ��ѹ

	}

	public void SaveUncompressFile(String s) {
		try {
			
			br.write(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

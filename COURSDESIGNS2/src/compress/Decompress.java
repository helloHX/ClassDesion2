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

	public void loadHuffmanTree() {// 从文件中获取haffman树
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

	public void startDecompress() {// 将byte流转换为String形式
		File fileDecompress = new File(decompressFileName);
		long fileLength = fileDecompress.length();
		int tailbyte = (int) (fileLength % 1000);
		byte[] contentByte = new byte[1000];
		
		byte[] tailContenByte = new byte[tailbyte];
		try {

			bos = new BufferedOutputStream(new FileOutputStream(tragetFile,true));
			br = new BufferedWriter(new OutputStreamWriter(bos, "GBK"));
			
				for (int i = 0; i < fileLength / 1000; i++) {//分次读取减小内存压力
				
					dataStream.read(contentByte);
					
					decode(bytesToHexString(contentByte));
				}
				
				dataStream.read(tailContenByte);//读取不整1000的内容
				decode(bytesToHexString(tailContenByte));

				br.close();
				bos.close();// 关闭写入文件刘

			
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("解压完成");
	}

	public String bytesToHexString(byte[] src) {// 将byte二进制转换为二进制字符串

		StringBuilder stringBuilder = new StringBuilder();
		String shortByte;//为添加前置零的短二进制字符串
		int byteLength = 0;
		
		for (int i = 0; i < src.length; i++) {
			
			shortByte = Integer.toBinaryString(src[i]).substring(0);//将byte转换为二进制字符串
			byteLength = 7 - shortByte.length();//当前byte的长度

			if (shortByte.length() != 7) {
				for (int j = 0; j < byteLength; j++) {
					shortByte = "0" + shortByte;
				}
			}
			
			stringBuilder = stringBuilder.append(shortByte);
		}
		return stringBuilder.toString();
	}

	public void decode(String byteString) {// 解码将二进制字符串解码


		StringBuffer binaryStringBuffer = new StringBuffer();
		byteString = maintainString + byteString;//将上一次解压的剩余的内容添加在末尾
		
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
			maintainString = subString;//将当前段的最后内容保留
		}
		
		SaveUncompressFile(binaryStringBuffer.toString());// 解压

	}

	public void SaveUncompressFile(String s) {
		try {
			
			br.write(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

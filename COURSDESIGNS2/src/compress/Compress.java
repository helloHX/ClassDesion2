package compress;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import compress.HuffmanTree.Node;

public class Compress {

	private String compressFileName;
	private Map<Character, String> encodeMap;
	private String tragetFile;
	private HuffmanTree<Character> huffmanTree;
	private FileOutputStream saveStream;
	private String maintainBinaryStrring = "";
	private BufferedOutputStream bos;

	public Compress() {

	}

	public Compress(String compressFileName) {
		int suffixNameIndex = compressFileName.lastIndexOf('.');
		tragetFile = compressFileName.substring(0, suffixNameIndex + 1) + "dat";
		this.compressFileName = compressFileName;

		buildHuffmanTree();
		saveHuffmanTree();
		startCompress();

		System.out.println("ѹ�����");
	}

	public void buildHuffmanTree() {// ���ı����ж�ȡ��ͬʱ���ɱ���
		Map<Character, Integer> weightingMap = new HashMap<>();
		try {

			BufferedInputStream fileInputStrem = new BufferedInputStream(
					new FileInputStream(new File(compressFileName)));

			BufferedReader fileInput = new BufferedReader(
					new InputStreamReader(fileInputStrem, "utf-8"));

			String letterLine;
			while ((letterLine = fileInput.readLine()) != null) {
				char[] letters = letterLine.toCharArray();

				for (int i = 0; i < letters.length; i++) {
					int Amont = 0;
					if (weightingMap.get(letters[i]) != null)
						Amont = weightingMap.get(letters[i]);
					weightingMap.put(letters[i], Amont + 1);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		Set<Entry<Character, Integer>> entrySet = weightingMap.entrySet();// ��ȡentry����
		List<Entry<Character, Integer>> entryArray = new ArrayList<>(entrySet);// �������

		Node[] nodes = new Node[entrySet.size()];

		for (int i = 0; i < entrySet.size(); i++) {
			Map.Entry<Character, Integer> oneEntry = entryArray.get(i);
			nodes[i] = new Node(oneEntry.getKey());
			nodes[i].weight = oneEntry.getValue();
		}
		huffmanTree = new HuffmanTree<Character>(nodes);
		this.encodeMap = huffmanTree.getEncodeMap();// �õ���ǰ�ļ��ַ��ļ���ֵ��

	}

	public void saveHuffmanTree() {
		ObjectOutputStream saveHuffmanTree = null;
		try {

			saveHuffmanTree = new ObjectOutputStream(new BufferedOutputStream(
					new FileOutputStream("huffman.dat")));

			saveHuffmanTree.writeObject(huffmanTree);

			System.out.println("save");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				saveHuffmanTree.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void startCompress() {// ���ļ��ļ����ж�ȡ��Ϊ��������׼��
		BufferedInputStream inputStream = null;

		try {

			bos = new BufferedOutputStream(new FileOutputStream(tragetFile,
					true));
			inputStream = new BufferedInputStream(new FileInputStream(
					compressFileName));
			BufferedReader bReader = new BufferedReader(new InputStreamReader(
					inputStream, "utf-8"));

			StringBuffer text = new StringBuffer();
			String line = "";
			int readSize = 0;

			while ((line = bReader.readLine()) != null) {
				text.append(line);
				readSize++;
				if (readSize == 2) {// 5��һ��ѹ�������ڶԴ��ļ���ѹ��
					ChangeToBinary(text.toString(), false);
					text.setLength(0);
					readSize = 0;
				}
			}
			ChangeToBinary(text.toString(), true);
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ChangeToBinary(String text, boolean isLast) {// ���ļ�����ת��
		char[] letterArray = text.toCharArray();

		StringBuffer binaryStringBuffer = new StringBuffer();

		for (int i = 0; i < letterArray.length; i++) {// ���ļ��������ת��
			binaryStringBuffer.append(encodeMap.get(letterArray[i]));
		}

		ChangeToByte(binaryStringBuffer, isLast);
	}

	public void ChangeToByte(StringBuffer binaryStringBuffer, boolean isLast) {// ���������ļ�ת���ɶ���������

		StringBuffer tallStringBuffer = new StringBuffer(maintainBinaryStrring);
		tallStringBuffer.append(binaryStringBuffer);

		byte[] byteArray;

		if (isLast) {
			byteArray = new byte[binaryStringBuffer.length() / 7 + 1];
		} else {
			byteArray = new byte[binaryStringBuffer.length() / 7];
		}

		int i;
		for (i = 0; i < binaryStringBuffer.length() / 7; i++) {
			byteArray[i] = Byte.parseByte(
					binaryStringBuffer.substring((i + 1) * 7 - 7, (i + 1) * 7),
					2);
		}

		maintainBinaryStrring = binaryStringBuffer.substring(i * 7);

		if ((isLast && !maintainBinaryStrring.equals("null"))
				&& !maintainBinaryStrring.equals("")) {

			int tailLength = 7 - maintainBinaryStrring.length();
			for (int j = 0; j < tailLength; j++) {
				maintainBinaryStrring += 0;
			}
			byteArray[i] = Byte.parseByte(maintainBinaryStrring, 2);
		}

		SaveIntoFile(byteArray);
	}

	public void SaveIntoFile(byte[] byteArray) {
		try {

			bos.write(byteArray, 0, byteArray.length);

		} catch (IOException e) {
			System.out.println("error:д��ʱ���ִ���");
		}
	}

	public static void main(String[] args) {
		Compress compress = new Compress("canada.bmp");
	}

}


package tpoint24;

public class CalculateRatio {

	private double[] temp1 = new double[3];
	private double[] temp2 = new double[2];

	private double sum;
	private int[] cardArray = new int[4];
	private char[] operator = { '+', '-', '*', '/' };
	private double[] scard = new double[4];
	private boolean isCorrect = false;

	public static void main(String[] args) {

		CalculateRatio nAnalyzePoker = new CalculateRatio();
		nAnalyzePoker.allResult();
	}

	public CalculateRatio() {

	}

	/**
	 * ������������������
	 * 
	 * @param number1
	 *            ����1
	 * @param number2
	 *            ����2
	 * @param operator
	 *            ����3
	 * @return
	 */
	public static double calcute(double number1, double number2, char operator) {
		if (operator == '+') {
			return number1 + number2;
		} else if (operator == '-') {
			return number1 - number2;
		} else if (operator == '*') {
			return number1 * number2;
		} else if (operator == '/' && number2 != 0) {
			return number1 / number2;
		} else {
			return -1;
		}
	}

	public void search() {

		// ��һ�η��õķ���
		for (int i = 0; i < 4; i++) {

			// �ڶ��η��õķ���
			for (int j = 0; j < 4; j++) {

				// �����η��õķ���
				for (int k = 0; k < 4; k++) {

					// ���ȼ���������������֣�����3��������൱�����ŵ�����
					for (int m = 0; m < 3; m++) {
						if (scard[m + 1] == 0 && operator[i] == '/') {
							break;
						}

						temp1[m] = calcute(scard[m], scard[m + 1], operator[i]);
						temp1[(m + 1) % 3] = scard[(m + 2) % 4];
						temp1[(m + 2) % 3] = scard[(m + 3) % 4];

						// ��ȷ�����ȼ�����������֣���������൱��ʣ������������˳�򴢴���temp1������
						// ��������ѡ���ȼ���������������֣�����������൱�ڵڶ�������
						for (int n = 0; n < 2; n++) {
							if (temp1[n + 1] == 0 && operator[j] == '/') {
								break;
							}

							temp2[n] = calcute(temp1[n], temp1[n + 1],
									operator[j]);
							temp2[(n + 1) % 2] = temp1[(n + 2) % 3];

							// ��ȷ�����ȼ�����������֣���������൱��ʣ������������˳�򴢴���temp2������
							if (temp2[1] == 0 && operator[k] == '/') {
								break;
							}

							sum = calcute(temp2[0], temp2[1], operator[k]);

							if (sum == 24) {
								isCorrect = true;
								String expression = "";

								if (m == 0 && n == 0) {
									expression = "((" + (int) scard[0]
											+ operator[i] + (int) scard[1]
											+ ")" + operator[j]
											+ (int) scard[2] + ")"
											+ operator[k] + (int) scard[3]
											+ "=" + (int) sum;
								} else if (m == 0 && n == 1) {
									expression = "(" + (int) scard[0]
											+ operator[i] + (int) scard[1]
											+ ")" + operator[k] + "("
											+ (int) scard[2] + operator[j]
											+ (int) scard[3] + ")=" + (int) sum;
								} else if (m == 1 && n == 0) {
									expression = "(" + (int) scard[0]
											+ operator[j] + "("
											+ (int) scard[1] + operator[i]
											+ (int) scard[2] + "))"
											+ operator[k] + (int) scard[3]
											+ "=" + (int) sum;
								} else if (m == 2 && n == 0) {
									expression = "(" + (int) scard[0]
											+ operator[j] + (int) scard[1]
											+ ")" + operator[k] + "("
											+ (int) scard[2] + operator[i]
											+ (int) scard[3] + ")=" + (int) sum;
								} else if (m == 2 && n == 0) {
									expression = (int) scard[0] + operator[k]
											+ "(" + (int) scard[1]
											+ operator[j] + "("
											+ (int) scard[2] + operator[i]
											+ (int) scard[3] + "))="
											+ (int) sum;
								}

								System.out.println(expression);
							}
						}
					}
				}
			}
		}
	}

	public void allResult() {
		int allcount = 0;
		int count = 0;
		for (int i = 1; i < 53; i++) {
			for (int j = i + 1; j < 53; j++) {
				for (int k = j + 1; k < 53; k++)
					for (int l = k + 1; l < 53; l++) {
						allcount++;
						if (isCorrectExpression(i, j, k, l)) {
							count++;
						}
					}
			}
		}

		double rate = (double) count / allcount;
		System.out.println("���п��ܵ���Ϲ��У�" + allcount);
		System.out.println("���Ϊ24����Ϲ��У� " + count);
		System.out.println("�ɹ��ļ�����:" + rate);
	}

	/**
	 * ��������������4�����ж����ܲ������ٸ��������24����ʽ
	 * 
	 * @param i
	 * @param j
	 * @param k
	 * @param l
	 * @return
	 */
	public boolean isCorrectExpression(int num1, int num2, int num3, int num4) {
		for (int a = 0; a < 4; a++)
			for (int b = 0; b < 4; b++) {
				if (b == a) {
					continue;
				}

				for (int c = 0; c < 4; c++) {
					if (c == a || c == b) {
						continue;
					}

					for (int d = 0; d < 4; d++) {
						if (d == a || d == b || d == c) {
							continue;
						}

						cardArray[a] = num1;
						cardArray[b] = num2;
						cardArray[c] = num3;
						cardArray[d] = num4;

						for (int m = 0; m < 4; m++) {
							scard[m] = (double) cardArray[m] % 13;
							if (cardArray[m] % 13 == 0) {
								scard[m] = 13;
							}
						}

						search();

						if (isCorrect) {
							isCorrect = false;
							return true;
						}
					}
				}
			}

		return false;
	}
}

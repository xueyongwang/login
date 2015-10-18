package com.wxy.or.core;

public class LP {
	private int m; // 约束条件的个数
	private int n; // 变量个数
	private int m1; // <=的约束条件个数
	private int m2; // =的约束条件个数
	private int m3; // >=的约束条件个数
	private int error; // 判断是否是错误的
	private int basic[];
	private int nonbasic[];
	private double a[][]; // 约束条件的系数矩阵
	private double minmax; // 目标函数的最大值或最小值

	public LP(double minmax, int m, int n, int m1, int m2, int m3,
			double a[][], double x[]) {
		double value;
		this.error = 0;
		this.minmax = minmax;
		this.m = m;
		this.n = n;
		this.m1 = m1;
		this.m2 = m2;
		this.m3 = m3;
		if (m != m1 + m2 + m3) {
			this.error = 1;
		}
		// 系数矩阵的行数
		this.a = new double[m + 2][];
		for (int i = 0; i < m + 2; i++) {
			// 每一行系数矩阵的列数
			this.a[i] = new double[n + m + m3 + 1];
		}
		// 基变量的个数
		this.basic = new int[m + 2];
		// 非基变量的个数
		this.nonbasic = new int[n + m3 + 1];
		for (int i = 0; i <= m + 1; i++) {
			for (int j = 0; j <= n + m + m3; j++) {
				this.a[i][j] = 0.0;
			}
		}
		// 非基变量
		for (int j = 0; j <= n + m3; j++) {
			nonbasic[j] = j;
		}
		// 基变量
		for (int i = 1, j = n + m3 + 1; i <= m; i++, j++) {
			basic[i] = j;
		}
		// 约束条件》=的添加变量在系数矩阵的系数
		for (int i = m - m3 + 1, j = n + 1; i <= m; i++, j++) {
			this.a[i][j] = -1;
			this.a[m + 1][j] = -1;
		}
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				value = a[i - 1][j - 1];
				this.a[i][j] = value;
			}
			value = a[i - 1][n];
			if (value < 0) {
				error = 1;
			}
			this.a[i][0] = value;
		}
		for (int j = 1; j <= n; j++) // 价值系数
		{
			value = x[j - 1];
			this.a[0][j] = value * minmax;
		}
		for (int j = 1; j <= n; j++) {
			value = 0;
			for (int i = m1 + 1; i <= m; i++)
				value += this.a[i][j];
			this.a[m + 1][j] = value;
		}

	}

	// 换入变量
	public int research_in(int objrow) {
		int col = 0;
		for (int j = 1; j <= this.n + this.m3; j++) {
			if (this.nonbasic[j] <= this.n + this.m1 + this.m3
					&& this.a[objrow][j] > 10e-8) {
				col = j;
				break;
			}
		}
		return col;
	}

	// 换出变量
	public int research_out(int col) {
		double temp = -1;
		int row = 0;
		for (int i = 1; i <= this.m; i++) {
			double val = this.a[i][col];
			if (val > 10e-8) {
				val = this.a[i][0] / val;
				if (val < temp || temp == -1) {
					row = i;
					temp = val;
				}
			}
		}
		return row;
	}

	// 换入和换出变量交换
	public void swapbasic(int row, int col) {
		int temp = this.basic[row];
		this.basic[row] = this.nonbasic[col];
		this.nonbasic[col] = temp;
	}

	// 交换规则找出主元并化简
	public void pivot(int row, int col) {
		for (int j = 0; j <= this.n + this.m3; j++) {
			if (j != col) {
				this.a[row][j] = this.a[row][j] / this.a[row][col];
			}
		}
		this.a[row][col] = 1.0 / this.a[row][col];
		for (int i = 0; i <= this.m + 1; i++) {
			if (i != row) {
				for (int j = 0; j <= this.n + this.m3; j++) {
					if (j != col) {
						this.a[i][j] = this.a[i][j] - this.a[i][col]
								* this.a[row][j];
						if (Math.abs(this.a[i][j]) < 10e-8)
							this.a[i][j] = 0;
					}
				}
				this.a[i][col] = -this.a[i][col] * this.a[row][col];
			}
		}
		swapbasic(row, col);
	}

	// 判断有无界解
	public int simplex(int objrow) {
		int row = 0;
		while (true) {
			int col = research_in(objrow);
			if (col > 0) {
				row = research_out(col);
			} else {
				return 0;
			}
			if (row > 0) {
				pivot(row, col);
			} else {
				return 2;
			}
		}
	}

	// 判断有无可行解
	public int phase1() {
		this.error = simplex(this.m + 1);
		if (this.error > 0) {
			return this.error;
		}
		for (int i = 1; i <= this.m; i++) {
			if (this.basic[i] > this.n + this.m1 + this.m3) {
				if (this.a[i][0] > 10e-8) {
					return 3;
				}
				for (int j = 1; j <= this.n; j++) {
					if (Math.abs(this.a[i][j]) >= 10e-8) {
						pivot(i, j);
						break;
					}
				}
			}
		}
		return 0;
	}

	public int phase2() {
		return simplex(0);
	}

	// 判断是无可行解还是无界解
	public int compute() {
		if (this.error > 0)
			return this.error;
		if (this.m != this.m1) {
			this.error = phase1();
			if (this.error > 0)
				return this.error;
		}
		return phase2();
	}

	public String solve() {
		error = compute();
		String s = "";
		switch (error) {
		case 0:
			s = output();
			break;
		case 1:
			// System.out.println("输入数据错误!");
			s = "输入数据错误!";
			return s;

		case 2:
			// System.out.println("无界解!");
			s = "无界解!";
			return s;

		case 3:
			// System.out.println("无可行解!");
			s = "无可行解!";
			return s;

		default:
			s = "不懂";
			return s;

		}
		return s;
	}

	// 输出最优解
	public String output() {

		String s = "";
		int basicp[] = new int[n + 1];
		for (int i = 0; i <= n; i++) {
			basicp[i] = 0;
		}
		for (int i = 1; i <= m; i++) {
			if (basic[i] >= 1 && basic[i] <= n) {
				basicp[basic[i]] = i;
			}
		}
		for (int i = 1; i <= n; i++) {
			System.out.print("x" + i + "=");
			if (basicp[i] != 0) {
				s += "x" + i + "=" + a[basicp[i]][0] + "\n";
				// System.out.print(a[basicp[i]][0]);
			} else {
				s += "x" + i + "=" + "0,";
				// System.out.print("0");
			}
			// System.out.println();
		}
		System.out.println("最优值:" + -minmax * a[0][0]);
		s += "最优值:" + -minmax * a[0][0];
		return s;
	}
}

package com.rmpl;

import java.math.BigInteger;


public class RSAEncryptionDecryption {

    // 计算最大公约数
    public static BigInteger gcd(BigInteger a, BigInteger b) {
        while (!b.equals(BigInteger.ZERO)) {
            BigInteger temp = b;
            b = a.mod(b);
            a = temp;
        }
        return a;
    }

    // 使用扩展欧几里得算法求d (即私钥)
    public static BigInteger modInverse(BigInteger e, BigInteger phi) {
        BigInteger[] result = extendedEuclid(e, phi);
        return result[0].mod(phi);  // 结果可能为负，需要取模
    }

    // 扩展欧几里得算法，用来求解 ax + by = gcd(a,b)
    public static BigInteger[] extendedEuclid(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO)) {
            return new BigInteger[]{BigInteger.ONE, BigInteger.ZERO, a};  // {x, y, gcd(a, b)}
        } else {
            BigInteger[] vals = extendedEuclid(b, a.mod(b));
            BigInteger x = vals[1];
            BigInteger y = vals[0].subtract((a.divide(b)).multiply(vals[1]));
            return new BigInteger[]{x, y, vals[2]};
        }
    }

    public static void main(String[] args) {
        // 给定素数 P 和 Q
        BigInteger P = BigInteger.valueOf(11);
        BigInteger Q = BigInteger.valueOf(3);

        // 步骤1：计算 n = P * Q
        BigInteger n = P.multiply(Q);
        System.out.println("n (P*Q): " + n);

        // 步骤2：计算 欧拉函数 φ(n) = (P-1)*(Q-1)
        BigInteger phi = (P.subtract(BigInteger.ONE)).multiply(Q.subtract(BigInteger.ONE));
        System.out.println("φ(n): " + phi);

        // 步骤3：选择公钥指数 e，要求 1 < e < φ(n) 且 e 与 φ(n) 互质
        BigInteger e = BigInteger.valueOf(3);  // 给定 e = 3
        if (!gcd(e, phi).equals(BigInteger.ONE)) {
            throw new IllegalArgumentException("e 和 φ(n) 必须互质");
        }
        System.out.println("公钥指数 e: " + e);

        // 步骤4：计算私钥指数 d，满足 e * d ≡ 1 (mod φ(n))
        BigInteger d = modInverse(e, phi);
        System.out.println("私钥指数 d: " + d);

        // 步骤5：公钥 (e, n)，私钥 (d, n)
        System.out.println("公钥 (e, n): (" + e + ", " + n + ")");
        System.out.println("私钥 (d, n): (" + d + ", " + n + ")");

        // 步骤6：加密消息 m = 7
        BigInteger message = BigInteger.valueOf(7);
        BigInteger cipher = message.modPow(e, n);
        System.out.println("加密后的数据 (cipher): " + cipher);

        // 步骤7：解密
        BigInteger decryptedMessage = cipher.modPow(d, n);
        System.out.println("解密后的数据 (message): " + decryptedMessage);
    }
}

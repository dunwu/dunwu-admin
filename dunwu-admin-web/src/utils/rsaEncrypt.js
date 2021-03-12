import JSEncrypt from 'jsencrypt/bin/jsencrypt.min'

// 密钥对生成 http://web.chacuo.net/netrsakeypair

const publicKey =
  'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDFcVZJh2aD7x++heBdsxHgUjr+5UVJvGDsk94XB0QkUMwz5+SwFlkPq79WaLKTdec7RwbuKRRJLS84QjqwoH59EZgIE6P7uTgeLaNXkXEZ9V+rlDgQEt0B0+QsT5jDffUYPjvJOQHukJsk+1xiNFCrdcjk971UCVms8FnaFCsQJQIDAQAB'

const privateKey =
  'MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMVxVkmHZoPvH76F4F2zEeBSOv7lRUm8YOyT3hcHRCRQzDPn5LAWWQ+rv1ZospN15ztHBu4pFEktLzhCOrCgfn0RmAgTo/u5OB4to1eRcRn1X6uUOBAS3QHT5CxPmMN99Rg+O8k5Ae6QmyT7XGI0UKt1yOT3vVQJWazwWdoUKxAlAgMBAAECgYAMN0KJcuReDiULdEKffLBpQQbMnBufWXv9s/n665FeiQk5WB2g4qEVne2AR4k7VzSJSSm3aB8//JjkhP4XAFCsoq3A8IY6/hUcqZVr/hoaEhVP/4u9lYQtAA1n3Mr3KLuXE3mc72w1L7bvUoa9hKhf/nduRTzm1nfayx4n/QeiuQJBAP1NH+s9GfGjXgCtsu6TF5Wu41CT+8w/EzFNeMHHL9oHepNO+Mou5Gnxb9D8u6u7958dvYmOtsx/4NytAlwTPgcCQQDHi9vq1aBGtATFqk/Wh+7D1iC5sdjZe/VJqzz/w1Yj/plBuUubbTPGSTJ1oGydEZFWxJ0yvGKwKtvTEOE3K3VzAkEAtZ/nkzVstOb/MF1cP9UYxCtyswRpODrB+Egknk3E8gEK0zV9DvwwrAaCtseBidJt65nXnhrG2DuZcKJdXsgtYQJBAJYnXNXJ6HArmAexj6Ar63P71eZFuJp2122W6DDBKTmupF47c3RCaRpFDC5NCE3s4UO8cVmzPkO4DHA3nHEqR5cCQCUGGH4RyplyOa4iz6937nS6glsO7NCI9kO4SfHqxKX2RFU8JtyYFjcSuIFUkYY8yPz58Rm5xa8JMKHEDR9/9Jc='

// 加密
export function encrypt(txt) {
  const encryptor = new JSEncrypt()
  encryptor.setPublicKey(publicKey) // 设置公钥
  return encryptor.encrypt(txt) // 对需要加密的数据进行加密
}

// 解密
export function decrypt(txt) {
  const encryptor = new JSEncrypt()
  encryptor.setPrivateKey(privateKey)
  return encryptor.decrypt(txt)
}

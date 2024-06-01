# scx-http-proxy

生成一个 RSA 私钥

```
./openssl genrsa -out "./ca.key"
```

或

```
./openssl genrsa -out "./ca.key" -aes256
```

请求证书

```
openssl req -out "./ca.crt" -new -key "./ca.key" -x509 -days 18250 -utf8 -subj "/CN=scx567888/C=CN/ST=吉林/L=长春/O=scx567888/emailAddress=scx567888@outlook.com"
```
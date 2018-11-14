package activemq.xmg.com.ras_android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PrivateKey;
import java.security.PublicKey;

import activemq.xmg.com.ras_android.encrypt.Base64Utils;
import activemq.xmg.com.ras_android.encrypt.RSAUtils;
import activemq.xmg.com.ras_android.sign.RSA;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    /**
     * 文本
     */
    @BindView(R.id.ed_text)
    EditText edText;
    /**
     * 加密
     */
    @BindView(R.id.rsa_btn_jiami)
    Button rsaBtnJiami;
    @BindView(R.id.tv_text_ras_jiami)
    TextView tvTextRasJiami;
    @BindView(R.id.rsa_btn_jiemi)
    Button rsaBtnJiemi;
    @BindView(R.id.tv_text_ras_jiemi)
    TextView tvTextRasJiemi;
    /**
     * 解密
     */
    @BindView(R.id.base64_btn_jiami)
    Button base64BtnJiami;
    @BindView(R.id.tv_text_base64_jiami)
    TextView tvTextBase64Jiami;
    @BindView(R.id.base64_btn_jiemi)
    Button base64BtnJiemi;
    @BindView(R.id.tv_text_base64_jiemi)
    TextView tvTextBase64Jiemi;

    /**
     * 签名 与 验证
     */
    @BindView(R.id.rsa_sign_btn_jiami)
    Button rsaSignBtnJiami;
    @BindView(R.id.tv_text_ras_sign_jiami)
    TextView tvTextRasSignJiami;
    @BindView(R.id.rsa_sign_btn_jiemi)
    Button rsaSignBtnJiemi;
    @BindView(R.id.tv_text_ras_sign_jiemi)
    TextView tvTextRasSignJiemi;

    private static String content = null;
    /* 密钥内容 base64 code */
//    private static String PUCLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+iRXVDOsZikPvaIwzTDhcKx3r\n" +
//            "SZNbB/H/MrdUj/GkiSgDL7bTXjyb0cAwefD+/JxXBy6EMuPzBMt7flTWNXGBUNvw\n" +
//            "HpaUPicdVAH4h8V0PvUiQKG/pS6oynMvARjZIHWBg8VEqaTcBdpuq+1jhtDxhuBM\n" +
//            "SFpt7b8gpWo//BG0ZQIDAQAB";

    private static String PUCLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDKyi7wgUh3aRhLv63tUw94LxqK\n" +
            "SDobMGZ6/BgZy+ktLuaadCJeky5JzWNZ8As8aB3ZdtnbtNJ9W91tlSHlSHob5e8j\n" +
            "IFsRWwYLdw7llkI7HVHXlLTQRS0uFtXh3Y93+ugd1IfEJmqN741eBlm/5UjF/DKw\n" +
            "74VriMdMyacou1VhEQIDAQAB";

//    private static String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAL6JFdUM6xmKQ+9o\n" +
//            "jDNMOFwrHetJk1sH8f8yt1SP8aSJKAMvttNePJvRwDB58P78nFcHLoQy4/MEy3t+\n" +
//            "VNY1cYFQ2/AelpQ+Jx1UAfiHxXQ+9SJAob+lLqjKcy8BGNkgdYGDxUSppNwF2m6r\n" +
//            "7WOG0PGG4ExIWm3tvyClaj/8EbRlAgMBAAECgYAX3y8IEWVHPuaCEVQ3fR42lgRa\n" +
//            "nU5EAnvUYHNNufcpiTGlLI44bz8iuqXcrPp/yACCetjeIU4j/X7NCyfv6qQ8ux/0\n" +
//            "WdGY4WZtc9EV38vgxzlfOHWrtJ1qVBX6vbs8TZabaz9XSaE+u+akhGACf5dHm4HN\n" +
//            "uhwDIvtu0AwBzwMIBQJBAN5BI8q0S5EI3nu4Bi3ZzssFRwh9TD8Fa91TPntFGi0J\n" +
//            "q3iGTq2qb2j3TKOn0lZBVg82yicNlxklOemwWEqxDlcCQQDbdw2+2y9MNVJSxOvO\n" +
//            "wEKdzcvimB1m7896GcWRpOp6/BBZyj8A20QztpEmJ5v9V8sFIjiVqdWlWWar7Lqr\n" +
//            "9SWjAkBRcQ87hSu3nsdgEIP7IzgavvlTjA53fXYUKR/ZLe40mLmDtbt4+d5PWWd1\n" +
//            "BNcXkmOFua8D9n/qz/BTyLHh1NWLAkEAl6MA6lhDq+JDyVCqpaYN4T7qmtwDpLYZ\n" +
//            "owHfkqxiHyu+mGu3cH4P97MzQyunCjr42ck1U6OPLLpCyJO+v0WZBQJASReT45oU\n" +
//            "Xvp/eK/JEdMu68GFzDp9gbsKpRRNv2/fL+ZCRzEWzkElfDWmJy5g/FhkEatgfAuZ\n" +
//            "Cxl0w8M0aLiBQw==";

    private static String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMrKLvCBSHdpGEu/\n" +
            "re1TD3gvGopIOhswZnr8GBnL6S0u5pp0Il6TLknNY1nwCzxoHdl22du00n1b3W2V\n" +
            "IeVIehvl7yMgWxFbBgt3DuWWQjsdUdeUtNBFLS4W1eHdj3f66B3Uh8Qmao3vjV4G\n" +
            "Wb/lSMX8MrDvhWuIx0zJpyi7VWERAgMBAAECgYEAuuqHP4l6uQ32pgNklkakEJwi\n" +
            "M2mFnYAMRbnNoSFxqQL87Uq0T0YMOVyGAeUfBivVr5c8EaXnCkM9ov7+Ai++1Am0\n" +
            "SvaBpmPMwJuWO6+64WHhbSwIUWdOuCWjxNdde64LL0fthBbwA+0GJ4ZHQpMqW3os\n" +
            "/U2N708hDRTZ2qR2rgECQQDuW1lKwVAx/nCA4mqCyKSyPeUJjHwYwXIWDXI2VZFz\n" +
            "zUB4kHUizhVTFrj2nLKxee8PgtuowXtXN5K7LYGDuWKxAkEA2cze0BwicsKraoBG\n" +
            "pzXtwAYJ0g5MMwgOfQfXg/2g7Rl6Pge+RBuQC/ViypKDD7SWT5xR1/FaNYj6p9MY\n" +
            "vK28YQJBAJ4eJR7NHHj9s/4btNJrba38Fzr9Ybk+YoM0ikZ2/xVV3GXeVoYvNcA3\n" +
            "u52FF5laLPcaNMM/DqYt7H2U02zhhxECQHIF5WhCMm6uJ5ucCGElxJBRGPcVcSMO\n" +
            "RreCUXvSXluCiRLMbvmtDF6WWt/+kAq62i5XgilxO27nhYIy67JrqkECQDCnXDkr\n" +
            "XwLAIq/17uWLjHMGKuS3Dn1ZMh3shap4NdhujuC2Ktwrd7sacgyzrKJQObyT5eCG\n" +
            "FsWk9mBohFKce2Y=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setListener();
    }

    /**
     * 或者edText输入的内容
     */
    private void getContent() {
//       content="SFDDDDFSFSFSDFFSFSDFSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSDFSDFSDFFSFSDFSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSDFSDFSDFFSFSDFSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSDFSDFSDFFSFSDFSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSDFSDFSDFFSFSDFSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSDFSDFSDFFSFSDFSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSDFSDFSDFFSFSDFSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSDFSDFSDFFSFSDFSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSDFSDFSDFFSFSDFSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSDFSDFSDFFSFSDFSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSDFSDFSDFFSFSDFSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSDFSDFSDFFSFSDFSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSDFSDFSDFFSFSDFSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSDFSDFSDFFSFSDFSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSDFSDFSDFFSFSDFSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSDFSDFSDFSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSFSFSDFSDFSDFSDFSDFSFSDFSFSSDFSDFSDFSDFSFSDFSFSDF";
//       content = "143SDFFSFSDFS423";
        content = edText.getText().toString().trim();
    }


    private void setListener() {
        //==========================================================================================
        /**
         * 签名
         */
        rsaSignBtnJiami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContent();
                String sign = RSA.sign(content, PRIVATE_KEY, "utf-8");
                tvTextRasSignJiami.setText(sign);
            }
        });

        /**
         * 验证
         */
        rsaSignBtnJiemi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContent();
                String sign = tvTextRasSignJiami.getText().toString().trim();
//                content = "143SDFFSFSDFS423";
                boolean verify = RSA.verify(content, sign, PUCLIC_KEY, "utf-8");
                tvTextRasSignJiemi.setText(verify + "");
            }
        });

        //==========================================================================================

        /**
         * RAS加密
         */
        rsaBtnJiami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContent();
                Toast.makeText(MainActivity.this, "RSA加密", Toast.LENGTH_SHORT).show();
                /**加密内容*/

                try {
//                    PublicKey publicKey = RSAUtils.loadPublicKey(PUCLIC_KEY);
//                    /**公钥加密*/
//                    byte[] encryptByte = RSAUtils.encryptData(content.getBytes(), publicKey);
//                    String afterencrypt = Base64Utils.encode(encryptByte);

                    String afterencrypt = RSAUtils.encrypt(PUCLIC_KEY, content);
                    Log.d("TAG", "afterencrypt=" + afterencrypt);
                    tvTextRasJiami.setText(afterencrypt);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        /**
         * RAS解密
         */
        rsaBtnJiemi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContent();
                Toast.makeText(MainActivity.this, "RSA解密", Toast.LENGTH_SHORT).show();
                String jiami = tvTextRasJiami.getText().toString().trim();
                Log.d("TAG", "jiami=" + jiami);
                /**服务端使用私钥解密*/
                try {
//                    PrivateKey privateKey = RSAUtils.loadPrivateKey(PRIVATE_KEY);
//                    /**私钥解密*/
//                    byte[] decryptByte = RSAUtils.decryptData(Base64Utils.decode(jiami), privateKey);
//                    String decryptStr = new String(decryptByte);
                    String decryptStr = RSAUtils.decrypt(PRIVATE_KEY, jiami);
                    Log.d("TAG", "decryptStr=" + decryptStr);
                    tvTextRasJiemi.setText(decryptStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //==========================================================================================
        /**
         * Base64加密
         */
        base64BtnJiami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContent();
                Toast.makeText(MainActivity.this, "Base64加密", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "content=" + content);
                String encode = Base64Utils.encode(content.getBytes());
                Log.d("TAG", "encode=" + encode);
                tvTextBase64Jiami.setText(encode);
            }
        });

        /**
         * Base64解密
         */
        base64BtnJiemi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContent();
                Toast.makeText(MainActivity.this, "Base64解密", Toast.LENGTH_SHORT).show();
                String str = tvTextBase64Jiami.getText().toString().trim();
                Log.d("TAG", "str=" + str);
                byte[] decode = Base64Utils.decode(str);
                String decryptStr = new String(decode);
                Log.d("TAG", "decryptStr=" + decryptStr);
                tvTextBase64Jiemi.setText(decryptStr);
            }
        });

    }


}

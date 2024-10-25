package com.example.spring_yao.service;

public interface MailService {

    /**
     * 發送可驗證mail連結
     * @author Yao
     * @param to,subject,text
     * Last Modify author Yao Date: 2024/9/30 Ver:1.0
     * change Description
     * */
    void brSendValidateLink(String to, String subject, String text) throws Exception;

    /**
     * call Google API 取得 access token
     * @author Yao
     * @param code
     * Last Modify author Yao Date: 2024/10/1 Ver:1.0
     * change Description
     * */
    String brGetGoogleAccessToken(String code) throws Exception;

    /**
     * 使用 Google Access Token 獲取使用者的 Gmail 資訊
     * @author Yao
     * @param token
     * Last Modify author Yao Date: 2024/10/1 Ver:1.0
     * change Description
     * */
    String brGetUserEmail(String token) throws Exception;

    /**
     * Google 驗證通過，查詢使用者的 Gmail 後直接登入
     * @author Yao
     * @param email
     * Last Modify author Yao Date: 2024/10/1 Ver:1.0
     * change Description
     * */
    String brCheckEmailLogin(String email) throws Exception;
}

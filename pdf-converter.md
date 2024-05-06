# pdf-converter.md

## 1. DB로 부터 PDF 변환 대상건 조회

###

```sql
/*
TB_ECL113L 테이블에서 LBRY_PRCS_TPCD 열의 값이 '04'이고 PDF_TRFM_STCD 열의 값이 'W'인 모든 열을 선택합니다.
*/
SELECT * FROM TB_ECL113L
WHERE LBRY_PRCS_TPCD = '04'
  AND PDF_TRFM_STCD='W'
```

## 2. EDMS 변환 대상건 요청

```java
// 원본문서 다운로드(http)
boolean getFileForExternal(
    String lbryDocMtNo, // 문서 ID
    String swnlPath, // 다운로드 위치
    UserBscsInfoVo nowUserBLngInfo // ECL 사용자 정보
);
```

### 3. PDF 문서 변환

```java
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.io.IOException;
import java.net.URISyntaxException;

boolean ignore_cache = true;
String passwd = "";
String hdv_domain = "http://10.10.141.74:8101";
String file_extension = ".hwp";
String source = "__template/default/sample_ko-KR.hwp"
String convert_context;
if (file_extension.equals(".hwp") || file_extension.equals(".hwpx") || file_extension.equals(".odt")) {
    convert_context = "/hwp";
} else if (file_extension.equals(".doc") || file_extension.equals(".docx")) {
    convert_context = "/word";
} else if (file_extension.equals(".ppt") || file_extension.equals(".pptx")) {
    convert_context = "/show";
} else if (file_extension.equals(".xls") || file_extension.equals(".xlsx")) {
    convert_context = "/cell";
} else if (file_extension.equals(".htm") || file_extension.equals(".html")) {
    convert_context = "/common";
} else {
    return "error : Unsupported file format.";
}

String api_url = hdv_domain + convert_context + "/doc2pdf?file_path=" + URLEncoder.encode(source, "UTF-8");
if (ignore_cache) {
    api_url += "&ignore_cache=true"
}
if (passwd != null && !passwd.isEmpty()) {
    api_url += "&password=" + URLEncoder.encode(passwd, "UTF-8");
}

HttpClient client = HttpClient.newHttpClient();
HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(api_url))
        .header("Content-Type", "application/json")
        .build();

try {
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    System.out.println("Status Code: " + response.statusCode());
    System.out.println("Response Body: " + response.body());
} catch (IOException | InterruptedException e) {
    System.err.println("Request failed: " + e.getMessage());
}
```

### 4. PDF 문서 변환 업로드

```java
// PDF 변환 사본 등록
boolean addRenditionAfterUpload(
    String lbryDocMtNo, // 원본 문서 ID
    String fileFullPath // 파일 전체 경로
);    
```

### 5 . DB 업데이트

```sql
/*
TB_ECL113L 테이블에서 LBRY_DOC_MT_NO 열의 값이 'DOC000092913481'인 행의 열 값 업데이트
*/
UPDATE TB_ECL113L
SET 
    PDF_TRFM_STCD = 'S', -- VARCHAR2(1) / PDF 변환 작업 진행 단계를 알려주는 상태 값
    PDF_TRFM_FILE_SIZE = '82 KB', -- VARCHAR2(10) / PDF 변환 파일 크기
    PDF_TRFM_ERR_NO = 'R', -- VARCHAR2(8) / PDF변환 오류 번호
    PDF_TRFM_WKNG_ID = 'RDOC000092913424-d43fc618-c355-44db-b7e3-c2a16513d166', -- VARCHAR2(64) / PDF 변환 작업 요청의 고유 ID
    MLT_TRFM_SRVR_PT_VAL = 'B', -- VARCHAR2(1) / PDF 변환서버 유형
    MLT_TRFM_SRVR_IP = '10.101.102.104', -- VARCHAR2(15) / 서버 다중화 구현시 서버 구분에 사용되는 변환 서버 IP
    TRFM_REQT_DTL_DTTM = '23/12/01 01:13:06.360658000', -- TIMESTAMP / 변환작업 요청 일시
    TRFM_STRT_DTL_DTTM = '23/12/01 01:13:10.000000000', -- TIMESTAMP / PDF 변환 작업 실제 시작 일시
    TRFM_CMPL_DTL_DTTM = '23/12/01 01:13:21.000000000', -- TIMESTAMP / 변환 작업 완료 일시
    TRFM_REREQT_NCNT = '0', -- NUMBER(10) / 실패한 작업에 대한 재변환 시도 횟수
    PDF_TRFM_RQRD_HR_VAL = '10.453s', -- VARCHAR2(20) / PDF 변환본 생성에 소요된 초단위 시간 값
    PDF_PGE_AQTY = '1' -- NUMBER(10) / PDF 변환 문서의 페이지 수
WHERE LBRY_DOC_MT_NO = 'DOC000092913481'
```
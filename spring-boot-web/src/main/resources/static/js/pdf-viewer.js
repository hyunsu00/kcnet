var PDF_VWER_URL_1 = "http://127.0.0.1:8080/pdfviewer";           // 대민
var PDF_VWER_URL_2 = "http://127.0.0.1:8080/pdfviewer";           // 관세행정
var PDF_VWER_URL_3 = "http://127.0.0.1:8080/pdfviewer";           // 관세지원1
var PDF_VWER_URL_4 = "http://127.0.0.1:8080/pdfviewer";           // 관세지원2
var PDF_VWER_URL_5 = "http://127.0.0.1:8080/pdfviewer";           // 정보분석
var PDF_VWER_URL_6 = "http://127.0.0.1:8080/pdfviewer";           // 여행자
var PDF_VWER_URL_7 = "http://127.0.0.1:8080/pdfviewer";           // 여행자민원

var PDFVwerForm = function() {
    var bfForm;
    this.form;
    this.makeDefaultForm = function() {
        this.form = document.createElement('form');
        this.form.setAttribute('method', 'post');
        this.form.setAttribute('name', 'iElctLbryRdngCond');
        this.form.setAttribute('id', 'ECL_iElctLbryRdngCond');
        this.form.setAttribute('enctype', 'application/json');
        this.form.setAttribute('style', 'display: none');
        bfForm = document.forms['ECL_iElctLbryRdngCond'];
        if (bfForm == undefined) {
            document.body.appendChild(this.form);
        } else {
            document.body.replaceChild(this.form, bfForm);
        }
    };
    this.addFormInfo = function(key, value) {
        value = value ? value : '';
        var input = document.createElement('input');
        input.setAttribute('name', key);
        input.setAttribute('value', String(value));
        this.form.appendChild(input);
    };
    this.cleanForm = function() {
        bfForm = document.forms['ECL_iElctLbryRdngCond'];
        if (bfForm != undefined) {
            document.body.removeChild(bfForm);
        }
    };
};

function cf_openPdfVwer(prmt, vwerTp) {
    var vwerForm = new PDFVwerForm();
    var url = null;
    var pdfDwnlPsblYn = 'Y';

    if (prmt['pdfDwnlPsblYn'] != undefined && prmt['pdfDwnlPsblYn'] != '') { // PDF다운로드가능여부
        pdfDwnlPsblYn = prmt['pdfDwnlPsblYn'];
    }

    vwerForm.makeDefaultForm();
    vwerForm.addFormInfo('clsfCd', prmt['clsfCd']);                         // 소분류코드
    vwerForm.addFormInfo('lbryBsopReffNo', prmt['lbryBsopReffNo']);         // 서고업무참조번호
    vwerForm.addFormInfo('enlrLbryBsopReffNo', prmt['enlrLbryBsopReffNo']); // 확장서고업무참조번호
    vwerForm.addFormInfo('userSgn', prmt['userSgn']);                       // 사용자부호(ID)
    vwerForm.addFormInfo('vwerTp', vwerTp);                                 // 뷰어구분
    vwerForm.addFormInfo('pdfDwnlPsblYn', pdfDwnlPsblYn);                   // PDF다운로드가능여부

    if (prmt['lbryDocMtNo'] != undefined) {
        $.each(prmt['lbryDocMtNo'], function() {
            vwerForm.addFormInfo('lbryDocMtNo', this);              // 서고문서관리번호
        });
    }

    if (vwerTp == '1') {
        url = PDF_VWER_URL_1;           // 대민
    } else if (vwerTp == '2') {
        url = PDF_VWER_URL_2;           // 관세행정
    } else if (vwerTp == '3') {
        url = PDF_VWER_URL_3;           // 관세지원1
    } else if (vwerTp == '4') {
        url = PDF_VWER_URL_4;           // 관세지원2
    } else if (vwerTp == '5') {
        url = PDF_VWER_URL_5;           // 정보분석
    } else if (vwerTp == '6') {
        url = PDF_VWER_URL_6;           // 여행자
    } else if (vwerTp == '7') {
        url = PDF_VWER_URL_7;           // 여행자민원
    }

    var pop = cf_openPopup(url, screen.width, screen.height, 'ECL_iElctLbryRdngCond');

    if (pop) {
        vwerForm.cleanForm();
    }
}

function cf_openPopup(url, width, height, id) {
    console.log("url = ", url, "width = ", width, "height = ", height, "document.forms[", id, "]", document.forms[id]);

    var form = document.forms[id];
    var popupUrl = url + "?lbryDocMtNo=1&userTpcd=2&userId=3&cstmSgn=4&dvsnCd=5";

    // 팝업 창을 열기 위한 윈도우 객체 생성
    var popupWindow = window.open(popupUrl, '_blank', 'width=' + width + ',height=' + height);
            
    // 팝업이 차단된 경우
    if (!popupWindow || popupWindow.closed || typeof popupWindow.closed == 'undefined') {
        alert('팝업이 차단되었습니다. 팝업 차단을 해제해주세요.');
    }

    return true;
}

// DOMContentLoaded 이벤트 핸들러 등록
document.addEventListener('DOMContentLoaded', function() {
    // pdfViewer 보기
    document.getElementById('cf_openPdfVwer').addEventListener('click', function() {
        var prmt = {
            clsfCd: "clsfCd",
            lbryBsopReffNo: "lbryBsopReffNo",
            enlrLbryBsopReffNo: "enlrLbryBsopReffNo",
            userSgn: "userSgn",
            lbryDocMtNo: [1]
        };
        var vwerTp = 1;
        cf_openPdfVwer(prmt, vwerTp);
    });
});


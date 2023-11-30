-- 시퀀스 삭제
DROP SEQUENCE USER_SEQ;
DROP SEQUENCE NOTICE_SEQ;
DROP SEQUENCE NOTICE_ATTACH_SEQ;
DROP SEQUENCE INQUIRY_SEQ;
DROP SEQUENCE AUCTION_SEQ;
DROP SEQUENCE DRAW_SEQ;
DROP SEQUENCE EMONEY_SEQ;
DROP SEQUENCE INACTIVE_USER_SEQ;
DROP SEQUENCE INQUIRY_ATTACH_SEQ;
DROP SEQUENCE AUCTION_IMAGE_SEQ;
DROP SEQUENCE DRAW_IMAGE_SEQ;
DROP SEQUENCE AUCTION_WISHLIST_SEQ;
DROP SEQUENCE CHAT_SEQ;
DROP SEQUENCE BID_SEQ;
DROP SEQUENCE DRAW_ORDER_SEQ;
DROP SEQUENCE CATEGORY_SEQ;
DROP SEQUENCE ALARM_SEQ;
DROP SEQUENCE DRAW_WISHLIST_SEQ;
DROP SEQUENCE ART_SEQ;
DROP SEQUENCE DRAW_REVIEW_SEQ;

-- 시퀀스 생성
CREATE SEQUENCE USER_SEQ;
CREATE SEQUENCE NOTICE_SEQ;
CREATE SEQUENCE NOTICE_ATTACH_SEQ;
CREATE SEQUENCE INQUIRY_SEQ;
CREATE SEQUENCE AUCTION_SEQ;
CREATE SEQUENCE DRAW_SEQ;
CREATE SEQUENCE EMONEY_SEQ;
CREATE SEQUENCE INACTIVE_USER_SEQ;
CREATE SEQUENCE INQUIRY_ATTACH_SEQ;
CREATE SEQUENCE AUCTION_IMAGE_SEQ;
CREATE SEQUENCE DRAW_IMAGE_SEQ;
CREATE SEQUENCE AUCTION_WISHLIST_SEQ;
CREATE SEQUENCE CHAT_SEQ;
CREATE SEQUENCE BID_SEQ;
CREATE SEQUENCE DRAW_ORDER_SEQ;
CREATE SEQUENCE CATEGORY_SEQ;
CREATE SEQUENCE ALARM_SEQ;
CREATE SEQUENCE DRAW_WISHLIST_SEQ;
CREATE SEQUENCE ART_SEQ;
CREATE SEQUENCE DRAW_REVIEW_SEQ;

-- 테이블 삭제
DROP TABLE ALARM_T;
DROP TABLE ACCESS_T;
DROP TABLE LEAVE_USER_T;
DROP TABLE INACTIVE_USER_T;
DROP TABLE ANSWER_T;
DROP TABLE INQUIRY_ATTACH_T;
DROP TABLE INQUIRY_T;
DROP TABLE NOTICE_ATTACH_T;
DROP TABLE NOTICE_T;
DROP TABLE MESSAGE_T;
DROP TABLE CHAT_T;
DROP TABLE EMONEY_T;
DROP TABLE DRAW_REVIEW_T;
DROP TABLE DRAW_WISHLIST_T;
DROP TABLE DRAW_ORDER_T;
DROP TABLE DRAW_IMAGE_T;
DROP TABLE DRAW_T;
DROP TABLE AUCTION_WISHLIST_T;
DROP TABLE BID_T;
DROP TABLE AUCTION_IMAGE_T;
DROP TABLE AUCTION_T;
DROP TABLE ART_T;
DROP TABLE CATEGORY_T;
DROP TABLE USER_T;

-- 회원 테이블
CREATE TABLE USER_T (
	USER_NO	        NUMBER		               NOT NULL,
	EMAIL	        VARCHAR2(100 BYTE) UNIQUE  NOT NULL,
	PW	            VARCHAR2(64 BYTE)		   NOT NULL,
	NAME	        VARCHAR2(50 BYTE)		   NOT NULL,
	MOBILE	        VARCHAR2(15 BYTE)		   NOT NULL,
	GENDER	        VARCHAR2(2 BYTE)		   NULL,
	AGREE	        NUMBER		               NULL,
	STATE	        NUMBER		               NULL,
	JOINED_AT	    DATE		               NULL,
	POSTCODE	    VARCHAR2(5  BYTE)		   NULL,
	ROAD_ADDRESS	VARCHAR2(100 BYTE)		   NULL,
	JIBUN_ADDRESS	VARCHAR2(100 BYTE)		   NULL,
	DETAIL_ADDRESS  VARCHAR2(100 BYTE)		   NULL,
	NICKNAME	    VARCHAR2(50 BYTE)		   NOT NULL,
	INTRODUCTION	VARCHAR2(200 BYTE)		   NULL
);

-- 카테고리 테이블
CREATE TABLE CATEGORY_T (
	CATEGORY_NO	    NUMBER		       NOT NULL,
	CATEGORY_NAME	VARCHAR2(100 BYTE) NOT NULL
);

-- 작품(경매) 테이블
CREATE TABLE ART_T (
	ART_NO	    NUMBER		        NOT NULL,
	SELLER_NO	NUMBER		        NOT NULL,
	CATEGORY_NO	NUMBER		        NOT NULL,
	TITLE   	VARCHAR2(100 BYTE)	NOT NULL,
	CONTENTS	VARCHAR2(4000 BYTE)	NULL,
	ART_TYPE	NUMBER	        	NOT NULL,
	WIDTH	    NUMBER		        NULL,
	HEIGHT	    NUMBER		        NULL
);

-- 경매 테이블
CREATE TABLE AUCTION_T (
	AUCTION_NO	NUMBER		      NOT NULL,
	ART_NO	    NUMBER		      NOT NULL,
	START_PRICE	NUMBER		      NOT NULL,
    BUY_PRICE	NUMBER		      NULL,
	START_AT	DATE		      NOT NULL,
	END_AT	    DATE		      NULL,
	STATUS	    NUMBER	DEFAULT 0 NOT NULL
);

-- 경매 이미지 테이블
CREATE TABLE AUCTION_IMAGE_T (
	AUCTION_IMAGE_NO	NUMBER		       NOT NULL,
	AUCTION_NO	        NUMBER		       NOT NULL,
	PATH	            VARCHAR2(100 BYTE) NULL,
	FILESYSTEM_NAME	    VARCHAR2(300 BYTE) NULL,
	IMAGE_ORIGINAL_NAME	VARCHAR2(300 BYTE) NULL,
	HAS_THUMBNAIL	    NUMBER		       NULL
);

-- 입찰내역 테이블
CREATE TABLE BID_T (
	BID_NO	        NUMBER		       NOT NULL,
	AUCTION_NO	    NUMBER		       NOT NULL,
	BIDDER_NO	    NUMBER		       NULL,
	PRICE	        NUMBER		       NOT NULL,
	BID_AT	        DATE		       NULL,
	POSTCODE	    VARCHAR2(5  BYTE)  NULL,
	ROAD_ADDRESS	VARCHAR2(100 BYTE) NULL,
	JIBUN_ADDRESS	VARCHAR2(100 BYTE) NULL,
	DETAIL_ADDRESS	VARCHAR2(100 BYTE) NULL
);

-- 경매 찜목록 테이블
CREATE TABLE AUCTION_WISHLIST_T (
	AUCTION_WISH_NO	NUMBER		NOT NULL,
	AUCTION_NO	    NUMBER		NOT NULL,
	USER_NO	        NUMBER		NOT NULL,
	WISHED_AT	    DATE		NULL
);

-- 그려드림 테이블
CREATE TABLE DRAW_T (
	DRAW_NO	    NUMBER		       NOT NULL,
	SELLER_NO	NUMBER		       NULL,
	CATEGORY_NO	NUMBER		       NOT NULL,
	TITLE	    VARCHAR2(200 BYTE) NOT NULL,
    PRICE       NUMBER             NULL,
	CONTENTS	VARCHAR2(200 BYTE) NULL,
	WIDTH	    NUMBER		       NULL,
	HEIGHT	    NUMBER		       NULL,
    WORK_TERM   NUMBER             NULL
);

-- 그려드림 이미지 테이블
CREATE TABLE DRAW_IMAGE_T (
	DRAW_IMAGE_NO	    NUMBER		       NOT NULL,
	DRAW_NO	            NUMBER		       NOT NULL,
	PATH	            VARCHAR2(100 BYTE) NULL,
	FILESYSTEM_NAME	    VARCHAR2(300 BYTE) NULL,
	IMAGE_ORIGINAL_NAME	VARCHAR2(300 BYTE) NULL,
	HAS_THUMBNAIL	    NUMBER		       NULL
);

-- 그려드림 주문내역 테이블
CREATE TABLE DRAW_ORDER_T (
	ORDER_NO	   NUMBER		      NOT NULL,
	DRAW_NO	       NUMBER		      NOT NULL,
	BUYER_NO	   NUMBER		      NULL,
	ORDER_DATE	   DATE		          NULL,
	PRICE	       NUMBER		      NULL,
	RECEIVE_EMAIL  VARCHAR2(100 BYTE) NULL
);

-- 그려드림 찜목록 테이블
CREATE TABLE DRAW_WISHLIST_T (
	DRAW_WISH_NO  NUMBER  NOT NULL,
	DRAW_NO	      NUMBER  NOT NULL,
	USER_NO	      NUMBER  NOT NULL,
	WISHED_AT	  DATE	  NULL
);

-- 그려드림 리뷰 테이블
CREATE TABLE DRAW_REVIEW_T (
	DRAW_NO	        NUMBER		       NOT NULL,
	BUYER_NO	    NUMBER		       NULL,
	REVIEW_TITLE	VARCHAR2(100 BYTE) NOT NULL,
	REVIEW_CONTENTS	VARCHAR2(200 BYTE) NULL,
	RATING	        NUMBER		       NOT NULL,
	CREATED_AT	    DATE		       NOT NULL
);

-- E-MONEY입출금내역 테이블
CREATE TABLE EMONEY_T (
	EMONEY_NO	    VARCHAR2(100 BYTE) NOT NULL,
	USER_NO	        NUMBER		       NOT NULL,
	EMONEY_HISTORY  NUMBER		       NULL,
	EMONEY_DATE 	VARCHAR2(100 BYTE) NULL
);

-- 채팅방 테이블
CREATE TABLE CHAT_T (
	ROOM_ID	 NUMBER		NOT NULL,
	USER_NO	 NUMBER		NULL,
	USER_NO2 NUMBER		NULL
);

-- 채팅 메세지 테이블
CREATE TABLE MESSAGE_T (
	ROOM_ID	   NUMBER             NOT NULL,
	CONTENTS   VARCHAR2(200 BYTE) NULL,
	CHAT_DATE  DATE		          NULL
);

-- 공지사항 테이블
CREATE TABLE NOTICE_T (
	NOTICE_NO	NUMBER		        NOT NULL,
	TITLE	    VARCHAR2(100 BYTE)	NOT NULL,
	CONTENTS	VARCHAR2(4000 BYTE)	NULL,
	CREATED_AT	DATE		        NULL
);

-- 공지사항 첨부 테이블
CREATE TABLE NOTICE_ATTACH_T (
	NOTICE_ATTACH_NO   NUMBER		      NOT NULL,
	NOTICE_NO	       NUMBER		      NOT NULL,
	PATH	           VARCHAR2(100 BYTE) NULL,
	ORIGINAL_FILENAME  VARCHAR2(300 BYTE) NULL,
	FILESYSTEM_NAME	   VARCHAR2(300 BYTE) NULL
);

-- 1:1문의 테이블
CREATE TABLE INQUIRY_T (
	INQUIRY_NO	        NUMBER		        NOT NULL,
    USER_NO	            NUMBER		        NOT NULL,
	AUCTION_NO	        NUMBER		        NULL,
	DRAW_NO	            NUMBER		        NULL,
	TITLE	            VARCHAR2(100 BYTE)	NOT NULL,
	CONTENT	            VARCHAR2(4000 BYTE)	NOT NULL,
	INQUIRY_CREATED_AT	DATE		        NULL,
	INQUIRY_TYPE	    VARCHAR2(100 BYTE)  NOT NULL
);

-- 1:1문의 첨부 테이블
CREATE TABLE INQUIRY_ATTACH_T (
	INQUIRY_ATTACH_NO  NUMBER		      NOT NULL,
	INQUIRY_NO	       NUMBER		      NOT NULL,
	PATH	           VARCHAR2(100 BYTE) NULL,
	ORIGINAL_FILENAME  VARCHAR2(300 BYTE) NULL,
	FILESYSTEM_NAME	   VARCHAR2(300 BYTE) NULL
);

--1:1문의 댓글(답변) 테이블
CREATE TABLE ANSWER_T (
	INQUIRY_NO	NUMBER		       NOT NULL,
	CONTENTS	VARCHAR2(500 BYTE) NULL,
	CREATED_AT	DATE		       NULL,
	STATUS  	NUMBER		       NULL
);

-- 휴면회원 테이블
CREATE TABLE INACTIVE_USER_T (
	USER_NO	        NUMBER  		   NOT NULL,
	EMAIL	        VARCHAR2(100 BYTE) NOT NULL,
	PW	            VARCHAR2(64 BYTE)  NOT NULL,
	NAME	        VARCHAR2(50 BYTE)  NOT NULL,
	MOBILE	        VARCHAR2(15 BYTE)  NOT NULL,
	GENDER	        VARCHAR2(2 BYTE)   NULL,
	AGREE	        NUMBER		       NULL,
	STATE	        NUMBER		       NULL,
	JOINED_AT	    DATE		       NULL,
	POSTCODE	    VARCHAR2(5   BYTE)  NULL,
	ROAD_ADDRESS	VARCHAR2(100 BYTE) NULL,
	JIBUN_ADDRESS	VARCHAR2(100 BYTE) NULL,
	DETAIL_ADDRESS  VARCHAR2(100 BYTE) NULL,
	NICKNAME	    VARCHAR2(50  BYTE)  NULL,
	INTRODUCTION	VARCHAR2(200 BYTE)  NULL,
	INACTIVED_AT	DATE		       NULL
);

-- 탈퇴회원 테이블
CREATE TABLE LEAVE_USER_T (
	EMAIL	    VARCHAR2(50 BYTE) NULL,
	JOINED_AT	DATE		      NULL,
	LEAVED_AT	DATE		      NULL
);

-- 접속기록 테이블
CREATE TABLE ACCESS_T (
	EMAIL	  VARCHAR2(100 BYTE) NOT NULL,
	LOGIN_AT  DATE		         NOT NULL
);

-- 알림 테이블
CREATE TABLE ALARM_T (
	ALARM_NO    	NUMBER		       NOT NULL,
    USER_NO         NUMBER             NOT NULL,
	AUCTION_NO	    NUMBER		       NULL,
    DRAW_NO	        NUMBER		       NULL,
    NOTICE_NO       NUMBER             NULL,
	INQUIRY_NO	    NUMBER		       NULL,
	ALARM_CONTENTS	VARCHAR2(300 BYTE) NULL,
	ALARM_TYPE	    VARCHAR2(20 BYTE)  NULL,
	CREATED_AT	    DATE		       NULL
);




-- PK,FK 관계 맺기
ALTER TABLE USER_T ADD CONSTRAINT PK_USER_T PRIMARY KEY (USER_NO);

ALTER TABLE NOTICE_T ADD CONSTRAINT PK_NOTICE_T PRIMARY KEY (NOTICE_NO);

ALTER TABLE NOTICE_ATTACH_T ADD CONSTRAINT PK_NOTICE_ATTACH_T PRIMARY KEY (NOTICE_ATTACH_NO);

ALTER TABLE INQUIRY_T ADD CONSTRAINT PK_INQUIRY_T PRIMARY KEY (INQUIRY_NO);

ALTER TABLE AUCTION_T ADD CONSTRAINT PK_AUCTION_T PRIMARY KEY (AUCTION_NO);

ALTER TABLE DRAW_T ADD CONSTRAINT PK_DRAW_T PRIMARY KEY (DRAW_NO);

ALTER TABLE EMONEY_T ADD CONSTRAINT PK_EMONEY_T PRIMARY KEY (EMONEY_NO);

ALTER TABLE INACTIVE_USER_T ADD CONSTRAINT PK_INACTIVE_USER_T PRIMARY KEY (USER_NO);

ALTER TABLE INQUIRY_ATTACH_T ADD CONSTRAINT PK_INQUIRY_ATTACH_T PRIMARY KEY (INQUIRY_ATTACH_NO);

ALTER TABLE AUCTION_IMAGE_T ADD CONSTRAINT PK_AUCTION_IMAGE_T PRIMARY KEY (AUCTION_IMAGE_NO);

ALTER TABLE DRAW_IMAGE_T ADD CONSTRAINT PK_DRAW_IMAGE_T PRIMARY KEY (DRAW_IMAGE_NO);

ALTER TABLE AUCTION_WISHLIST_T ADD CONSTRAINT PK_AUCTION_WISHLIST_T PRIMARY KEY (AUCTION_WISH_NO);

ALTER TABLE CHAT_T ADD CONSTRAINT PK_CHAT_T PRIMARY KEY (ROOM_ID);

ALTER TABLE BID_T ADD CONSTRAINT PK_BID_T PRIMARY KEY (BID_NO);

ALTER TABLE DRAW_ORDER_T ADD CONSTRAINT PK_DRAW_ORDER_T PRIMARY KEY (ORDER_NO);

ALTER TABLE CATEGORY_T ADD CONSTRAINT PK_CATEGORY_T PRIMARY KEY (CATEGORY_NO);

ALTER TABLE ALARM_T ADD CONSTRAINT PK_ALARM_T PRIMARY KEY (ALARM_NO);

ALTER TABLE DRAW_WISHLIST_T ADD CONSTRAINT PK_DRAW_WISHLIST_T PRIMARY KEY (DRAW_WISH_NO);

ALTER TABLE ART_T ADD CONSTRAINT PK_ART_T PRIMARY KEY (ART_NO);

ALTER TABLE NOTICE_ATTACH_T ADD CONSTRAINT FK_NOTICE_T_TO_NOTICE_ATTACH_T_1 FOREIGN KEY (NOTICE_NO) REFERENCES NOTICE_T (NOTICE_NO) ON DELETE CASCADE;

ALTER TABLE INQUIRY_T ADD CONSTRAINT FK_AUCTION_T_TO_INQUIRY_T_1 FOREIGN KEY (AUCTION_NO) REFERENCES AUCTION_T (AUCTION_NO) ON DELETE SET NULL;

ALTER TABLE INQUIRY_T ADD CONSTRAINT FK_DRAW_T_TO_INQUIRY_T_1 FOREIGN KEY (DRAW_NO) REFERENCES DRAW_T (DRAW_NO) ON DELETE SET NULL;

ALTER TABLE INQUIRY_T ADD CONSTRAINT FK_USER_T_TO_INQUIRY_T_1 FOREIGN KEY (USER_NO) REFERENCES USER_T (USER_NO) ON DELETE CASCADE;

ALTER TABLE AUCTION_T ADD CONSTRAINT FK_ART_T_TO_AUCTION_T_1 FOREIGN KEY (ART_NO) REFERENCES ART_T (ART_NO) ON DELETE CASCADE;

ALTER TABLE DRAW_T ADD CONSTRAINT FK_USER_T_TO_DRAW_T_1 FOREIGN KEY (SELLER_NO) REFERENCES USER_T (USER_NO) ON DELETE SET NULL;

ALTER TABLE DRAW_T ADD CONSTRAINT FK_CATEGORY_T_TO_DRAW_T_1 FOREIGN KEY (CATEGORY_NO) REFERENCES CATEGORY_T (CATEGORY_NO) ON DELETE CASCADE;

ALTER TABLE EMONEY_T ADD CONSTRAINT FK_USER_T_TO_EMONEY_T_1 FOREIGN KEY (USER_NO) REFERENCES USER_T (USER_NO) ON DELETE CASCADE;

ALTER TABLE INQUIRY_ATTACH_T ADD CONSTRAINT FK_INQUIRY_T_TO_INQUIRY_ATTACH_T_1 FOREIGN KEY (INQUIRY_NO) REFERENCES INQUIRY_T (INQUIRY_NO) ON DELETE CASCADE;

ALTER TABLE AUCTION_IMAGE_T ADD CONSTRAINT FK_AUCTION_T_TO_AUCTION_IMAGE_T_1 FOREIGN KEY (AUCTION_NO) REFERENCES AUCTION_T (AUCTION_NO) ON DELETE CASCADE;

ALTER TABLE DRAW_IMAGE_T ADD CONSTRAINT FK_DRAW_T_TO_DRAW_IMAGE_T_1 FOREIGN KEY (DRAW_NO) REFERENCES DRAW_T (DRAW_NO) ON DELETE CASCADE;

ALTER TABLE AUCTION_WISHLIST_T ADD CONSTRAINT FK_AUCTION_T_TO_AUCTION_WISHLIST_T_1 FOREIGN KEY (AUCTION_NO) REFERENCES AUCTION_T (AUCTION_NO) ON DELETE CASCADE;

ALTER TABLE AUCTION_WISHLIST_T ADD CONSTRAINT FK_USER_T_TO_AUCTION_WISHLIST_T_1 FOREIGN KEY (USER_NO) REFERENCES USER_T (USER_NO) ON DELETE CASCADE;

ALTER TABLE CHAT_T ADD CONSTRAINT FK_USER_T_TO_CHAT_T_1 FOREIGN KEY (USER_NO) REFERENCES USER_T (USER_NO) ON DELETE SET NULL;

ALTER TABLE CHAT_T ADD CONSTRAINT FK_USER_T_TO_CHAT_T_2 FOREIGN KEY (USER_NO2) REFERENCES USER_T (USER_NO) ON DELETE SET NULL;

ALTER TABLE MESSAGE_T ADD CONSTRAINT FK_CHAT_T_TO_MESSAGE_T_1 FOREIGN KEY (ROOM_ID) REFERENCES CHAT_T (ROOM_ID) ON DELETE CASCADE;

ALTER TABLE BID_T ADD CONSTRAINT FK_AUCTION_T_TO_BID_T_1 FOREIGN KEY (AUCTION_NO) REFERENCES AUCTION_T (AUCTION_NO) ON DELETE CASCADE;

ALTER TABLE BID_T ADD CONSTRAINT FK_USER_T_TO_BID_T_1 FOREIGN KEY (BIDDER_NO) REFERENCES USER_T (USER_NO) ON DELETE SET NULL;

ALTER TABLE DRAW_ORDER_T ADD CONSTRAINT FK_DRAW_T_TO_DRAW_ORDER_T_1 FOREIGN KEY (DRAW_NO) REFERENCES DRAW_T (DRAW_NO) ON DELETE CASCADE;

ALTER TABLE DRAW_ORDER_T ADD CONSTRAINT FK_USER_T_TO_DRAW_ORDER_T_1 FOREIGN KEY (BUYER_NO) REFERENCES USER_T (USER_NO) ON DELETE SET NULL;

ALTER TABLE ALARM_T ADD CONSTRAINT FK_USER_T_TO_ALARM_T_1 FOREIGN KEY (USER_NO) REFERENCES USER_T (USER_NO) ON DELETE CASCADE;

ALTER TABLE ALARM_T ADD CONSTRAINT FK_AUCTION_T_TO_ALARM_T_1 FOREIGN KEY (AUCTION_NO) REFERENCES AUCTION_T (AUCTION_NO) ON DELETE SET NULL;

ALTER TABLE ALARM_T ADD CONSTRAINT FK_INQUIRY_T_TO_ALARM_T_1 FOREIGN KEY (INQUIRY_NO) REFERENCES INQUIRY_T (INQUIRY_NO) ON DELETE SET NULL;

ALTER TABLE ALARM_T ADD CONSTRAINT FK_DRAW_T_TO_ALARM_T_1 FOREIGN KEY (DRAW_NO) REFERENCES DRAW_T (DRAW_NO) ON DELETE SET NULL;

ALTER TABLE DRAW_WISHLIST_T ADD CONSTRAINT FK_DRAW_T_TO_DRAW_WISHLIST_T_1 FOREIGN KEY (DRAW_NO) REFERENCES DRAW_T (DRAW_NO) ON DELETE CASCADE;

ALTER TABLE DRAW_WISHLIST_T ADD CONSTRAINT FK_USER_T_TO_DRAW_WISHLIST_T_1 FOREIGN KEY (USER_NO) REFERENCES USER_T (USER_NO) ON DELETE CASCADE;

ALTER TABLE ART_T ADD CONSTRAINT FK_USER_T_TO_ART_T_1 FOREIGN KEY (SELLER_NO) REFERENCES USER_T (USER_NO) ON DELETE CASCADE;

ALTER TABLE ART_T ADD CONSTRAINT FK_CATEGORY_T_TO_ART_T_1 FOREIGN KEY (CATEGORY_NO) REFERENCES CATEGORY_T (CATEGORY_NO) ON DELETE CASCADE;

ALTER TABLE ANSWER_T ADD CONSTRAINT FK_INQUIRY_T_TO_ANSWER_T_1 FOREIGN KEY (INQUIRY_NO) REFERENCES INQUIRY_T (INQUIRY_NO) ON DELETE CASCADE;

ALTER TABLE DRAW_REVIEW_T ADD CONSTRAINT FK_DRAW_T_TO_DRAW_REVIEW_T_1 FOREIGN KEY (DRAW_NO) REFERENCES DRAW_T (DRAW_NO) ON DELETE CASCADE;

ALTER TABLE DRAW_REVIEW_T ADD CONSTRAINT FK_USER_T_TO_DRAW_REVIEW_T_2 FOREIGN KEY (BUYER_NO) REFERENCES USER_T (USER_NO) ON DELETE SET NULL;

-------------INSERT---------------
-- 관리자
INSERT INTO USER_T VALUES(USER_SEQ.NEXTVAL, 'admin01@naver.com',STANDARD_HASH('1111', 'SHA256'), '관리자', '010-0000-0000', 'F', '0', '0', SYSDATE, '00000', 'DORO', 'JIBUN', 'SANGSE', '관리자자', '관리자입니다.');

-- 회원
INSERT INTO USER_T VALUES(USER_SEQ.NEXTVAL, 'user01@naver.com', STANDARD_HASH('1111', 'SHA256'), '사용자1', '01011111111', 'M', 0, 0, TO_DATE('220111', 'YYMMDD'), '1111','서울', '가산동', 'KM타워', '용사1', '용사입니다');
INSERT INTO USER_T VALUES(USER_SEQ.NEXTVAL, 'user02@naver.com', STANDARD_HASH('2222', 'SHA256'), '사용자2', '01022222222', 'M', 0, 0, TO_DATE('220110', 'YYMMDD'), '2222','서울', '가산동', 'KM타워', '용사2', '용사입니다');
INSERT INTO USER_T VALUES(USER_SEQ.NEXTVAL, 'user03@naver.com', STANDARD_HASH('3333', 'SHA256'), '사용자3', '01033333333', 'M', 0, 0, TO_DATE('220109', 'YYMMDD'), '3333','서울', '가산동', 'KM타워', '용사3', '용사입니다');
INSERT INTO USER_T VALUES(USER_SEQ.NEXTVAL, 'user04@naver.com', STANDARD_HASH('4444', 'SHA256'), '사용자4', '01044444444', 'M', 0, 0, TO_DATE('220108', 'YYMMDD'), '4444','서울', '가산동', 'KM타워', '용사4', '용사입니다');
INSERT INTO USER_T VALUES(USER_SEQ.NEXTVAL, 'user05@naver.com', STANDARD_HASH('5555', 'SHA256'), '사용자5', '01055555555', 'M', 0, 0, TO_DATE('230109', 'YYMMDD'), '5555','서울', '가산동', 'KM타워', '용사5', '용사입니다');
INSERT INTO USER_T VALUES(USER_SEQ.NEXTVAL, 'user06@naver.com', STANDARD_HASH('6666', 'SHA256'), '사용자6', '01066666666', 'M', 0, 0, TO_DATE('230110', 'YYMMDD'), '6666','서울', '가산동', 'KM타워', '용사6', '용사입니다');
INSERT INTO USER_T VALUES(USER_SEQ.NEXTVAL, 'user07@naver.com', STANDARD_HASH('7777', 'SHA256'), '사용자7', '01077777777', 'M', 0, 0, TO_DATE('230111', 'YYMMDD'), '7777','서울', '가산동', 'KM타워', '용사7', '용사입니다');
INSERT INTO USER_T VALUES(USER_SEQ.NEXTVAL, 'user08@naver.com', STANDARD_HASH('8888', 'SHA256'), '사용자8', '01088888888', 'M', 0, 0, TO_DATE('230111', 'YYMMDD'), '7777','서울', '가산동', 'KM타워', '용사8', '용사입니다');
INSERT INTO USER_T VALUES(USER_SEQ.NEXTVAL, 'user09@naver.com', STANDARD_HASH('9999', 'SHA256'), '사용자9', '01099999999', 'M', 0, 0, TO_DATE('230111', 'YYMMDD'), '7777','서울', '가산동', 'KM타워', '용사9', '용사입니다');




-- 카테고리
INSERT INTO CATEGORY_T VALUES(CATEGORY_SEQ.NEXTVAL, '포토그래픽');
INSERT INTO CATEGORY_T VALUES(CATEGORY_SEQ.NEXTVAL, '원화');
INSERT INTO CATEGORY_T VALUES(CATEGORY_SEQ.NEXTVAL, '드로잉');
INSERT INTO CATEGORY_T VALUES(CATEGORY_SEQ.NEXTVAL, '판화');
INSERT INTO CATEGORY_T VALUES(CATEGORY_SEQ.NEXTVAL, '서예');
INSERT INTO CATEGORY_T VALUES(CATEGORY_SEQ.NEXTVAL, '회화');
INSERT INTO CATEGORY_T VALUES(CATEGORY_SEQ.NEXTVAL, '동양화');
INSERT INTO CATEGORY_T VALUES(CATEGORY_SEQ.NEXTVAL, '공예');
INSERT INTO CATEGORY_T VALUES(CATEGORY_SEQ.NEXTVAL, '디지털아트');
INSERT INTO CATEGORY_T VALUES(CATEGORY_SEQ.NEXTVAL, '조각품');
INSERT INTO CATEGORY_T VALUES(CATEGORY_SEQ.NEXTVAL, '기타');

------


-- 작품
INSERT INTO ART_T VALUES(ART_SEQ.NEXTVAL, 2, 3, '작품제목1', '작품내용1', 1, 30, 30);
INSERT INTO ART_T VALUES(ART_SEQ.NEXTVAL, 4, 2, '작품제목2', '작품내용2', 0, 40, 40);
INSERT INTO ART_T VALUES(ART_SEQ.NEXTVAL, 3, 4, '작품제목3', '작품내용3', 0, 50, 50);
INSERT INTO ART_T VALUES(ART_SEQ.NEXTVAL, 2, 3, '작품제목4', '작품내용4', 1, 60, 60);
INSERT INTO ART_T VALUES(ART_SEQ.NEXTVAL, 2, 4, '작품제목5', '작품내용5', 0, 100, 100);
INSERT INTO ART_T VALUES(ART_SEQ.NEXTVAL, 4, 2, '작품제목6', '작품내용6', 1, 120, 140);
INSERT INTO ART_T VALUES(ART_SEQ.NEXTVAL, 3, 4, '작품제목7', '작품내용7', 0, 140, 150);
INSERT INTO ART_T VALUES(ART_SEQ.NEXTVAL, 3, 3, '작품제목8', '작품내용8', 1, 160, 160);
INSERT INTO ART_T VALUES(ART_SEQ.NEXTVAL, 2, 4, '작품제목9', '작품내용9', 0, 180, 170);
INSERT INTO ART_T VALUES(ART_SEQ.NEXTVAL, 4, 2, '작품제목10', '작품내용10', 1, 220, 180);
INSERT INTO ART_T VALUES(ART_SEQ.NEXTVAL, 4, 4, '작품제목11', '작품내용11', 0, 320, 190);
INSERT INTO ART_T VALUES(ART_SEQ.NEXTVAL, 3, 4, '작품제목12', '작품내용12', 0, 420, 240);
INSERT INTO ART_T VALUES(ART_SEQ.NEXTVAL, 2, 4, '작품제목13', '작품내용13', 0, 520, 340);
INSERT INTO ART_T VALUES(ART_SEQ.NEXTVAL, 2, 3, '작품제목14', '작품내용14', 1, 620, 440);
INSERT INTO ART_T VALUES(ART_SEQ.NEXTVAL, 2, 4, '작품제목15', '작품내용15', 0, 720, 540);
INSERT INTO ART_T VALUES(ART_SEQ.NEXTVAL, 4, 2, '작품제목16', '작품내용16', 0, 820, 640);
INSERT INTO ART_T VALUES(ART_SEQ.NEXTVAL, 4, 2, '작품제목17', '작품내용17', 1, 920, 740);
INSERT INTO ART_T VALUES(ART_SEQ.NEXTVAL, 3, 2, '작품제목18', '작품내용18', 0, 820, 840);
INSERT INTO ART_T VALUES(ART_SEQ.NEXTVAL, 3, 2, '작품제목19', '작품내용19', 1, 720, 940);
INSERT INTO ART_T VALUES(ART_SEQ.NEXTVAL, 3, 4, '작품제목20', '작품내용20', 1, 620, 1140);
INSERT INTO ART_T VALUES(ART_SEQ.NEXTVAL, 4, 3, '작품제목21', '작품내용21', 0, 520, 2140);
INSERT INTO ART_T VALUES(ART_SEQ.NEXTVAL, 4, 2, '작품제목22', '작품내용22', 1, 420, 3140);
INSERT INTO ART_T VALUES(ART_SEQ.NEXTVAL, 3, 2, '작품제목23', '작품내용23', 1, 320, 4140);
INSERT INTO ART_T VALUES(ART_SEQ.NEXTVAL, 3, 4, '작품제목24', '작품내용24', 1, 320, 5140);
INSERT INTO ART_T VALUES(ART_SEQ.NEXTVAL, 4, 3, '작품제목25', '작품내용25', 0, 220, 6140);

-- 경매상품
INSERT INTO AUCTION_T VALUES(AUCTION_SEQ.NEXTVAL, 1, 3000, 9000, TO_DATE('2023-04-27 18:07:08', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2023-05-05 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 1);
INSERT INTO AUCTION_T VALUES(AUCTION_SEQ.NEXTVAL, 2, 2000, 7000, TO_DATE('2023-11-07 17:52:55', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2023-11-23 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 0);
INSERT INTO AUCTION_T VALUES(AUCTION_SEQ.NEXTVAL, 3, 10000, 20000, TO_DATE('2023-11-28 07:46:12', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2023-12-11 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 0);
INSERT INTO AUCTION_T VALUES(AUCTION_SEQ.NEXTVAL, 4, 20000, 80000, TO_DATE('2023-11-21 15:22:16', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2023-12-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), -1);
INSERT INTO AUCTION_T VALUES(AUCTION_SEQ.NEXTVAL, 5, 1000, 10000, TO_DATE('2023-11-25 13:26:21', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2023-12-12 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 0);

-- 경매 찜
INSERT INTO AUCTION_WISHLIST_T VALUES(AUCTION_WISHLIST_SEQ.NEXTVAL, 3, 3, TO_DATE('2023-11-11 04:20:00','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO AUCTION_WISHLIST_T VALUES(AUCTION_WISHLIST_SEQ.NEXTVAL, 1, 5, TO_DATE('2023-11-13 13:24:30','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO AUCTION_WISHLIST_T VALUES(AUCTION_WISHLIST_SEQ.NEXTVAL, 5, 7, TO_DATE('2023-11-24 09:24:33','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO AUCTION_WISHLIST_T VALUES(AUCTION_WISHLIST_SEQ.NEXTVAL, 5, 3, TO_DATE('2023-11-26 01:44:35','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO AUCTION_WISHLIST_T VALUES(AUCTION_WISHLIST_SEQ.NEXTVAL, 5, 2, TO_DATE('2023-11-27 23:50:40','YYYY-MM-DD HH24:MI:SS'));

-- E-MONEY 
INSERT INTO EMONEY_T VALUES(EMONEY_SEQ.NEXTVAL, 2, 500000, TO_DATE('2023-11-11 04:20:00','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO EMONEY_T VALUES(EMONEY_SEQ.NEXTVAL, 3, 10000000, TO_DATE('2023-11-11 04:20:00','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO EMONEY_T VALUES(EMONEY_SEQ.NEXTVAL, 4, 3000000, TO_DATE('2023-11-11 04:20:00','YYYY-MM-DD HH24:MI:SS'));

-- 그려드림 INSERT
INSERT INTO DRAW_T VALUES(DRAW_SEQ.NEXTVAL, 2, 3, '현대판 렘브란트', 1200000, '마치 현대에 있는 렘브란트처럼 그려드립니다.', 1024, 1024, 7);
INSERT INTO DRAW_T VALUES(DRAW_SEQ.NEXTVAL, 3, 2, '원화 그려드립니다.', 24000000, '1:1채팅 걸어주세요.', 1024, 1024, 14);
INSERT INTO DRAW_T VALUES(DRAW_SEQ.NEXTVAL, 4, 4, '귀여운 캐릭 판화', 579000, '귀여운 캐릭터만 그릴 수 있습니다.', 1024, 1024, 3);
INSERT INTO DRAW_T VALUES(DRAW_SEQ.NEXTVAL, 5, 5, '붓글씨로 그려주는 이쁜손글씨', 8900000, '붓글씨 손글씨 15년째 장인이 글씨 그려드립니다.', 1024, 1024, 6);
INSERT INTO DRAW_T VALUES(DRAW_SEQ.NEXTVAL, 6, 6, '유명한 작품 모방가능합니다.', 9999999, '회화 관련 유명 작가 모방한 그림체로 그려드립니다.', 1024, 1024, 2);
INSERT INTO DRAW_T VALUES(DRAW_SEQ.NEXTVAL, 7, 7, '동양화로 그리는 초상화', 19750000, '사진보내주시면 동양화로 초상화 그려드립니다.', 1024, 1024, 1);

-- 그려드림 이미지 INSERT
INSERT INTO DRAW_IMAGE_T VALUES(DRAW_IMAGE_SEQ.NEXTVAL, 1, '이미지 경로 1', '파일시스템이름1', '이미지 원본이름1', 1);
INSERT INTO DRAW_IMAGE_T VALUES(DRAW_IMAGE_SEQ.NEXTVAL, 2, '이미지 경로 2', '파일시스템이름2', '이미지 원본이름2', 1);
INSERT INTO DRAW_IMAGE_T VALUES(DRAW_IMAGE_SEQ.NEXTVAL, 3, '이미지 경로 3', '파일시스템이름3', '이미지 원본이름3', 0);
INSERT INTO DRAW_IMAGE_T VALUES(DRAW_IMAGE_SEQ.NEXTVAL, 4, '이미지 경로 4', '파일시스템이름4', '이미지 원본이름4', 1);
INSERT INTO DRAW_IMAGE_T VALUES(DRAW_IMAGE_SEQ.NEXTVAL, 5, '이미지 경로 5', '파일시스템이름5', '이미지 원본이름5', 0);

-- 그려드림 주문내역
INSERT INTO DRAW_ORDER_T VALUES(DRAW_ORDER_SEQ.NEXTVAL, 3, 4, TO_DATE('2023-10-13 13:24:30','YYYY-MM-DD HH24:MI:SS'), 5000, 'oppa@naver.com');
INSERT INTO DRAW_ORDER_T VALUES(DRAW_ORDER_SEQ.NEXTVAL, 2, 7, TO_DATE('2023-10-29 09:20:00','YYYY-MM-DD HH24:MI:SS'), 10000, 'khann23@google.com');
INSERT INTO DRAW_ORDER_T VALUES(DRAW_ORDER_SEQ.NEXTVAL, 2, 2, TO_DATE('2023-11-01 04:04:22','YYYY-MM-DD HH24:MI:SS'), 10000, 'goodie@naver.com');
INSERT INTO DRAW_ORDER_T VALUES(DRAW_ORDER_SEQ.NEXTVAL, 5, 6, TO_DATE('2023-11-08 08:08:08','YYYY-MM-DD HH24:MI:SS'), 90000, 'ironwoman@naver.com');
INSERT INTO DRAW_ORDER_T VALUES(DRAW_ORDER_SEQ.NEXTVAL, 4, 5, TO_DATE('2023-11-10 10:10:10','YYYY-MM-DD HH24:MI:SS'), 45000, 'bb00ng13@google.com');
 
-- 그려드림 찜
INSERT INTO DRAW_WISHLIST_T VALUES(DRAW_WISHLIST_SEQ.NEXTVAL, 1, 3, TO_DATE('2023-11-01 01:09:00','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO DRAW_WISHLIST_T VALUES(DRAW_WISHLIST_SEQ.NEXTVAL, 5, 3, TO_DATE('2023-11-02 02:08:01','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO DRAW_WISHLIST_T VALUES(DRAW_WISHLIST_SEQ.NEXTVAL, 5, 2, TO_DATE('2023-11-03 03:07:02','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO DRAW_WISHLIST_T VALUES(DRAW_WISHLIST_SEQ.NEXTVAL, 3, 7, TO_DATE('2023-11-04 04:06:03','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO DRAW_WISHLIST_T VALUES(DRAW_WISHLIST_SEQ.NEXTVAL, 4, 6, TO_DATE('2023-11-05 05:05:04','YYYY-MM-DD HH24:MI:SS'));

-- 그려드림 리뷰
INSERT INTO DRAW_REVIEW_T VALUES(2, 3, '리뷰제목1', '리뷰내용1', 4, TO_DATE('2023-11-11 14:22:55', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO DRAW_REVIEW_T VALUES(4, 2, '리뷰제목2', '리뷰내용2', 3, TO_DATE('2023-11-12 18:27:20', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO DRAW_REVIEW_T VALUES(6, 5, '리뷰제목3', '리뷰내용3', 5, TO_DATE('2023-11-13 12:11:52', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO DRAW_REVIEW_T VALUES(1, 4, '리뷰제목4', '리뷰내용4', 1, TO_DATE('2023-11-14 05:51:26', 'YYYY-MM-DD HH24:MI:SS'));

-- 채팅방
INSERT INTO CHAT_T VALUES(CHAT_SEQ.NEXTVAL, 3, 4);
INSERT INTO CHAT_T VALUES(CHAT_SEQ.NEXTVAL, 5, 6);
INSERT INTO CHAT_T VALUES(CHAT_SEQ.NEXTVAL, 7, 8);

-- 채팅 메세지
INSERT INTO MESSAGE_T VALUES(1, '메세지내용1', TO_DATE('2023-11-14 05:51:26', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO MESSAGE_T VALUES(2, '메세지내용2', TO_DATE('2023-11-14 05:51:26', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO MESSAGE_T VALUES(3, '메세지내용3', TO_DATE('2023-11-14 05:51:26', 'YYYY-MM-DD HH24:MI:SS'));

-- 입찰 내역
INSERT INTO BID_T VALUES(BID_SEQ.NEXTVAL, 1, 2, 3000, TO_DATE('2023-05-01 15:22:16', 'YYYY-MM-DD HH24:MI:SS'), '13132', '가산디지털로1길', '가산동453-2', 'KM타워 G호');
INSERT INTO BID_T VALUES(BID_SEQ.NEXTVAL, 1, 3, 3100, TO_DATE('2023-05-01 15:23:16', 'YYYY-MM-DD HH24:MI:SS'), '13132', '가산디지털로1길', '가산동453-3', 'KM타워 A호');
INSERT INTO BID_T VALUES(BID_SEQ.NEXTVAL, 1, 4, 3200, TO_DATE('2023-05-01 15:25:16', 'YYYY-MM-DD HH24:MI:SS'), '03132', '가산디지털로2길', '가산동453-4', 'KM타워 B호');
INSERT INTO BID_T VALUES(BID_SEQ.NEXTVAL, 1, 3, 3500, TO_DATE('2023-05-01 15:26:16', 'YYYY-MM-DD HH24:MI:SS'), '33132', '가산디지털로3길', '가산동453-5', 'KM타워 C호');
INSERT INTO BID_T VALUES(BID_SEQ.NEXTVAL, 1, 2, 4000, TO_DATE('2023-05-01 15:27:16', 'YYYY-MM-DD HH24:MI:SS'), '43132', '가산디지털로4길', '가산동453-6', 'KM타워 D호');
INSERT INTO BID_T VALUES(BID_SEQ.NEXTVAL, 1, 3, 4500, TO_DATE('2023-05-01 15:28:16', 'YYYY-MM-DD HH24:MI:SS'), '53132', '가산디지털로5길', '가산동453-7', 'KM타워 E호');
INSERT INTO BID_T VALUES(BID_SEQ.NEXTVAL, 1, 2, 4600, TO_DATE('2023-05-01 15:29:16', 'YYYY-MM-DD HH24:MI:SS'), '63132', '가산디지털로6길', '가산동453-8', 'KM타워 F호');
INSERT INTO BID_T VALUES(BID_SEQ.NEXTVAL, 1, 4, 5000, TO_DATE('2023-05-01 15:30:16', 'YYYY-MM-DD HH24:MI:SS'), '73132', '가산디지털로7길', '가산동453-9', 'KM타워 H호');
INSERT INTO BID_T VALUES(BID_SEQ.NEXTVAL, 1, 5, 5500, TO_DATE('2023-05-01 15:31:16', 'YYYY-MM-DD HH24:MI:SS'), '83132', '가산디지털로8길', '가산동453-10', 'KM타워 I호');
INSERT INTO BID_T VALUES(BID_SEQ.NEXTVAL, 1, 3, 6500, TO_DATE('2023-05-02 15:32:16', 'YYYY-MM-DD HH24:MI:SS'), '93132', '가산디지털로9길', '가산동453-11', 'KM타워 J호');

INSERT INTO BID_T VALUES(BID_SEQ.NEXTVAL, 2, 4, 2000, TO_DATE('2023-11-20 13:25:16', 'YYYY-MM-DD HH24:MI:SS'), '03132', '가산디지털로2길', '가산동453-4', 'KM타워 B호');
INSERT INTO BID_T VALUES(BID_SEQ.NEXTVAL, 2, 3, 3000, TO_DATE('2023-11-22 15:26:16', 'YYYY-MM-DD HH24:MI:SS'), '33132', '가산디지털로3길', '가산동453-5', 'KM타워 C호');
INSERT INTO BID_T VALUES(BID_SEQ.NEXTVAL, 2, 5, 3300, TO_DATE('2023-11-22 15:27:16', 'YYYY-MM-DD HH24:MI:SS'), '43132', '가산디지털로4길', '가산동453-6', 'KM타워 D호');
INSERT INTO BID_T VALUES(BID_SEQ.NEXTVAL, 2, 3, 3500, TO_DATE('2023-11-22 15:28:16', 'YYYY-MM-DD HH24:MI:SS'), '53132', '가산디지털로5길', '가산동453-7', 'KM타워 E호');
INSERT INTO BID_T VALUES(BID_SEQ.NEXTVAL, 2, 5, 4000, TO_DATE('2023-11-22 15:29:16', 'YYYY-MM-DD HH24:MI:SS'), '63132', '가산디지털로6길', '가산동453-8', 'KM타워 F호');
INSERT INTO BID_T VALUES(BID_SEQ.NEXTVAL, 2, 4, 5000, TO_DATE('2023-11-22 15:30:16', 'YYYY-MM-DD HH24:MI:SS'), '73132', '가산디지털로7길', '가산동453-9', 'KM타워 H호');
INSERT INTO BID_T VALUES(BID_SEQ.NEXTVAL, 2, 5, 5500, TO_DATE('2023-11-22 15:31:16', 'YYYY-MM-DD HH24:MI:SS'), '83132', '가산디지털로8길', '가산동453-10', 'KM타워 I호');
INSERT INTO BID_T VALUES(BID_SEQ.NEXTVAL, 2, 3, 6000, TO_DATE('2023-11-22 15:32:16', 'YYYY-MM-DD HH24:MI:SS'), '93132', '가산디지털로9길', '가산동453-11', 'KM타워 J호');

INSERT INTO BID_T VALUES(BID_SEQ.NEXTVAL, 3, 2, 10000, TO_DATE('2023-11-29 15:30:16', 'YYYY-MM-DD HH24:MI:SS'), '73132', '가산디지털로7길', '가산동453-9', 'KM타워 H호');
INSERT INTO BID_T VALUES(BID_SEQ.NEXTVAL, 3, 4, 13000, TO_DATE('2023-11-30 12:31:16', 'YYYY-MM-DD HH24:MI:SS'), '83132', '가산디지털로8길', '가산동453-10', 'KM타워 I호');
INSERT INTO BID_T VALUES(BID_SEQ.NEXTVAL, 3, 5, 17000, TO_DATE('2023-12-01 17:32:16', 'YYYY-MM-DD HH24:MI:SS'), '93132', '가산디지털로9길', '가산동453-11', 'KM타워 J호');

INSERT INTO BID_T VALUES(BID_SEQ.NEXTVAL, 5, 2, 1000, TO_DATE('2023-11-28 17:32:16', 'YYYY-MM-DD HH24:MI:SS'), '93132', '가산디지털로9길', '가산동453-11', 'KM타워 J호');


-- 접속기록
INSERT INTO ACCESS_T VALUES('user01@naver.com', TO_DATE('2023-11-21 15:22:16', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO ACCESS_T VALUES('user02@naver.com', TO_DATE('2023-11-21 15:22:16', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO ACCESS_T VALUES('user03@naver.com', TO_DATE('2023-11-21 15:22:16', 'YYYY-MM-DD HH24:MI:SS'));


-- 공지사항
INSERT INTO NOTICE_T VALUES(NOTICE_SEQ.NEXTVAL, '공지사항1번', '공지사항내용1', TO_DATE('2023-11-21 15:22:16', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO NOTICE_T VALUES(NOTICE_SEQ.NEXTVAL, '공지사항2번', '공지사항내용2', TO_DATE('2023-11-21 15:22:16', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO NOTICE_T VALUES(NOTICE_SEQ.NEXTVAL, '공지사항3번', '공지사항내용3', TO_DATE('2023-11-21 15:22:16', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO NOTICE_T VALUES(NOTICE_SEQ.NEXTVAL, '공지사항4번', '공지사항내용4', TO_DATE('2023-11-21 15:22:16', 'YYYY-MM-DD HH24:MI:SS'));

-- 공지사항 첨부파일 4개
INSERT INTO NOTICE_ATTACH_T VALUES(NOTICE_ATTACH_SEQ.NEXTVAL, 1, '파일경로', '원본파일이름1', '파일이름1');
INSERT INTO NOTICE_ATTACH_T VALUES(NOTICE_ATTACH_SEQ.NEXTVAL, 2, '파일경로', '원본파일이름2', '파일이름2');
INSERT INTO NOTICE_ATTACH_T VALUES(NOTICE_ATTACH_SEQ.NEXTVAL, 3, '파일경로', '원본파일이름3', '파일이름3');
INSERT INTO NOTICE_ATTACH_T VALUES(NOTICE_ATTACH_SEQ.NEXTVAL, 4, '파일경로', '원본파일이름4', '파일이름4');

-- 1:1문의
INSERT INTO INQUIRY_T VALUES(INQUIRY_SEQ.NEXTVAL, 2, 1, 1, '경매신고입니다.', '경매신고합니다!', TO_DATE('2023-11-21 15:22:16', 'YYYY-MM-DD HH24:MI:SS'),'경매신고');
INSERT INTO INQUIRY_T VALUES(INQUIRY_SEQ.NEXTVAL, 3, 2, 2, '그려드림신고입니다.', '그림이상함', TO_DATE('2023-11-21 15:22:16', 'YYYY-MM-DD HH24:MI:SS'),'그려드림 신고');
INSERT INTO INQUIRY_T VALUES(INQUIRY_SEQ.NEXTVAL, 4, 3, 3, '부적절한게시글이다', '욕설기재', TO_DATE('2023-11-21 15:22:16', 'YYYY-MM-DD HH24:MI:SS'),'부적절한 게시글');
INSERT INTO INQUIRY_T VALUES(INQUIRY_SEQ.NEXTVAL, 2, 2, 2, '광고성홍보.', '신고합니다.', TO_DATE('2023-11-21 15:22:16', 'YYYY-MM-DD HH24:MI:SS'),'광고성 홍보');
INSERT INTO INQUIRY_T VALUES(INQUIRY_SEQ.NEXTVAL, 3, 4, 4, '사칭,해킹문의드립니다.', '계정해킹당했다', TO_DATE('2023-11-21 15:22:16', 'YYYY-MM-DD HH24:MI:SS'),'사칭ㆍ해킹문의');
INSERT INTO INQUIRY_T VALUES(INQUIRY_SEQ.NEXTVAL, 3, 5, 5, '버그문의', '메인페이지오류', TO_DATE('2023-11-21 15:22:16', 'YYYY-MM-DD HH24:MI:SS'),'버그문의');
INSERT INTO INQUIRY_T VALUES(INQUIRY_SEQ.NEXTVAL, 5, 4, 4, '기타문의사항', '관리자님', TO_DATE('2023-11-21 15:22:16', 'YYYY-MM-DD HH24:MI:SS'),'기타');

-- 1:1문의 첨부파일
INSERT INTO INQUIRY_ATTACH_T VALUES(INQUIRY_ATTACH_SEQ.NEXTVAL, 1, '경로쓰1', '원본이름쓰', '파일파일');
INSERT INTO INQUIRY_ATTACH_T VALUES(INQUIRY_ATTACH_SEQ.NEXTVAL, 2, '경로쓰2', '원본이름쓰', '파일파일');
INSERT INTO INQUIRY_ATTACH_T VALUES(INQUIRY_ATTACH_SEQ.NEXTVAL, 3, '경로쓰3', '원본이름쓰', '파일파일');
INSERT INTO INQUIRY_ATTACH_T VALUES(INQUIRY_ATTACH_SEQ.NEXTVAL, 4, '경로쓰4', '원본이름쓰', '파일파일');
INSERT INTO INQUIRY_ATTACH_T VALUES(INQUIRY_ATTACH_SEQ.NEXTVAL, 5, '경로쓰5', '원본이름쓰', '파일파일');
INSERT INTO INQUIRY_ATTACH_T VALUES(INQUIRY_ATTACH_SEQ.NEXTVAL, 6, '경로쓰6', '원본이름쓰', '파일파일');
INSERT INTO INQUIRY_ATTACH_T VALUES(INQUIRY_ATTACH_SEQ.NEXTVAL, 7, '경로쓰7', '원본이름쓰', '파일파일');

-- 1:1문의 답변
INSERT INTO ANSWER_T VALUES(1, '안녕하세요 ! 관리자입니다. 무슨 경매신고인지 내용을 상세히 말해주시기 바랍니다. 감사합니다.',TO_DATE('2023-11-21 17:22:16', 'YYYY-MM-DD HH24:MI:SS'),1);
INSERT INTO ANSWER_T VALUES(2, '안녕하세요 ! 관리자입니다. drawaction은 개인의 독창성을 존중하는 사이트 입니다. 그림이 조금 이상하더라도 감안해주시길 바랍니다. 감사합니다.',TO_DATE('2023-11-21 17:22:16', 'YYYY-MM-DD HH24:MI:SS'),0);
INSERT INTO ANSWER_T VALUES(3, '안녕하세요 ! 관리자입니다. 문의해주신 글은 정상적으로 처리되었습니다. 감사합니다.',TO_DATE('2023-11-21 17:22:16', 'YYYY-MM-DD HH24:MI:SS'),1);
INSERT INTO ANSWER_T VALUES(4, '안녕하세요 ! 관리자입니다. 더 깨끗한 drawaction만들기에 참여해 주셔서 감사합니다.',TO_DATE('2023-11-21 17:22:16', 'YYYY-MM-DD HH24:MI:SS'),1);
INSERT INTO ANSWER_T VALUES(5, '안녕하세요 ! 관리자입니다. 회원가입시 입력했던 이메일로 임시 비밀번호를 발송하였으니 확인해주시길 바랍니다. 감사합니다.',TO_DATE('2023-11-21 17:22:16', 'YYYY-MM-DD HH24:MI:SS'),1);


--알림
INSERT INTO ALARM_T VALUES(ALARM_SEQ.NEXTVAL, 3, 2, NULL, NULL, NULL, '상회 입찰되었습니다.', '경매', TO_DATE('2023-11-10 15:22:16', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO ALARM_T VALUES(ALARM_SEQ.NEXTVAL, 4, 4, NULL, NULL, NULL, '응찰이 종료되었습니다.', '경매', TO_DATE('2023-11-11 15:22:16', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO ALARM_T VALUES(ALARM_SEQ.NEXTVAL, 5, 5, NULL, NULL, NULL, '작품이 낙찰되었습니다.', '경매', TO_DATE('2023-11-13 15:22:16', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO ALARM_T VALUES(ALARM_SEQ.NEXTVAL, 6, 3, NULL, NULL, NULL, '작품이 입찰되었습니다.', '경매', TO_DATE('2023-11-14 15:22:16', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO ALARM_T VALUES(ALARM_SEQ.NEXTVAL, 7, 4, NULL, NULL, NULL, '작품이 낙찰되었습니다.', '경매', TO_DATE('2023-11-14 15:22:16', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO ALARM_T VALUES(ALARM_SEQ.NEXTVAL, 6, 2, NULL, NULL, NULL, '응찰이 종료되었습니다.', '경매', TO_DATE('2023-11-15 15:22:16', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO ALARM_T VALUES(ALARM_SEQ.NEXTVAL, 5, NULL, 2, NULL, NULL, '주문이 접수되었습니다.', '그려드림', TO_DATE('2023-11-17 15:22:16', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO ALARM_T VALUES(ALARM_SEQ.NEXTVAL, 5, NULL, 4, NULL, NULL, '새로운 채팅이 있습니다.', '그려드림', TO_DATE('2023-11-18 15:22:16', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO ALARM_T VALUES(ALARM_SEQ.NEXTVAL, 4, NULL, 2, NULL, NULL, '배송이 시작되었습니다.', '그려드림', TO_DATE('2023-11-19 15:22:16', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO ALARM_T VALUES(ALARM_SEQ.NEXTVAL, 3, NULL,  2, NULL, NULL,'리뷰가 달렸습니다.', '그려드림', TO_DATE('2023-11-20 15:22:16', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO ALARM_T VALUES(ALARM_SEQ.NEXTVAL, 2, NULL, NULL, 1, NULL, '새로운 공지가 있습니다.', '공지사항', TO_DATE('2023-11-22 15:22:16', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO ALARM_T VALUES(ALARM_SEQ.NEXTVAL, 4, NULL, NULL, NULL, 1, '1:1문의 답변이 달렸습니다.', '1:1문의', TO_DATE('2023-11-23 15:22:16', 'YYYY-MM-DD HH24:MI:SS'));

-- 휴면
INSERT INTO INACTIVE_USER_T VALUES(7, 'user06@naver.com', STANDARD_HASH('6666', 'SHA256'), '사용자6', '01066666666', 'M', 0, 0, TO_DATE('230110', 'YYMMDD'), '6666','서울', '가산동', 'KM타워', '용사6', '용사입니다', TO_DATE('231110', 'YYMMDD'));
INSERT INTO INACTIVE_USER_T VALUES(8, 'user07@naver.com', STANDARD_HASH('7777', 'SHA256'), '사용자7', '01077777777', 'M', 0, 0, TO_DATE('230111', 'YYMMDD'), '7777','서울', '가산동', 'KM타워', '용사7', '용사입니다', TO_DATE('231111', 'YYMMDD'));

-- 탈퇴
INSERT INTO LEAVE_USER_T VALUES('user08@naver.com', TO_DATE('230111', 'YYMMDD'), TO_DATE('230911', 'YYMMDD'));
INSERT INTO LEAVE_USER_T VALUES('user09@naver.com', TO_DATE('230111', 'YYMMDD'), TO_DATE('230911', 'YYMMDD'));

COMMIT;
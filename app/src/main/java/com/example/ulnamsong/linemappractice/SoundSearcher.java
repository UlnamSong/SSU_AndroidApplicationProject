package com.example.ulnamsong.linemappractice;

/**
 * Created by Ulnamsong on 2015-12-05.
 */
public class SoundSearcher
{
    private static final char HANGUL_BEGIN_UNICODE = 44032; // 가
    private static final char HANGUL_LAST_UNICODE = 55203; // 힣
    private static final char HANGUL_BASE_UNIT = 588;//각자음 마다 가지는 글자수
    private static final char[] INITIAL_SOUND = { 'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ' };

    private static boolean isInitialSound(char searchar){
        for(char c:INITIAL_SOUND){
            if(c == searchar){
                return true;
            }
        }
        return false;
    }

    private static char getInitialSound(char c) {
        int hanBegin = (c - HANGUL_BEGIN_UNICODE);
        int index = hanBegin / HANGUL_BASE_UNIT;
        return INITIAL_SOUND[index];
    }

    private static boolean isHangul(char c) {
        return HANGUL_BEGIN_UNICODE <= c && c <= HANGUL_LAST_UNICODE;
    }

    private static boolean isBatchim(char c){
        if((c-HANGUL_BEGIN_UNICODE)%28>0)
            return true;
        else
            return false;
    }

    private static char substractBatchim(char c){
        char c1 = (char) (((((c-HANGUL_BEGIN_UNICODE)-(c-HANGUL_BEGIN_UNICODE)%28))/28)/21);    //초성
        char c2 = (char) (((((c-HANGUL_BEGIN_UNICODE)-(c-HANGUL_BEGIN_UNICODE)%28))/28)%21);    //중성
        //초성과 중성을 뽑아냈다

        return (char) (HANGUL_BEGIN_UNICODE+28*21*c1+28*c2);
        //초성과 중성을 합침
    }

    public SoundSearcher() { }
    public static boolean matchString(String value, String search){
        int t = 0;
        int seof = value.length() - search.length();
        int slen = search.length();
        if(seof < 0)
            return false; //검색어가 더 길면 false를 리턴한다.

        t = 0;
        while(t < slen){
            if(isInitialSound(search.charAt(t))==true && isHangul(value.charAt(t))){
                //만약 현재 char이 초성이고 value가 한글이면
                if(getInitialSound(value.charAt(t))==search.charAt(t))
                    //각각의 초성끼리 같은지 비교한다
                    t++;
                else
                    break;
            }else if(isBatchim(search.charAt(t))==false && isHangul(value.charAt(t)) && isBatchim(value.charAt(t))==true){
                //만약 현재 char가 받침이 없고, value는 받침이 있다면
                if(substractBatchim(value.charAt(t)) == search.charAt(t))
                    //예를들어, 신도림일때 시도리
                    t++;
                else
                    break;
            }else {
                //char이 초성이 아니라면
                if(value.charAt(t)==search.charAt(t))
                    //그냥 같은지 비교한다.
                    t++;
                    //else if->"시"로 검색할경우 "신도림"이 떠야한다.
                else
                    break;
            }
        }
        if(t == slen)
            return true; //모두 일치한 결과를 찾으면 true를 리턴한다.

        return false; //일치하는 것을 찾지 못했으면 false를 리턴한다.
    }
}

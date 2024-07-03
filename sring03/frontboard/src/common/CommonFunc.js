
export function formatDate(date) {
    var result = date.replace('T',' '); // T를 공백으로 변경
    var index = result.lastIndexOf(' ') // 초 앞에 있는 : 위치값, ' '은 yyyy-MM-dd 만 남김

    result = result.substring(0,index); // 초 뒤로 삭제

    return result;
}
import { useState } from "react";
function IncButton({count, onClick} ){
    //count(변수), setCount = 변수값을 조정할 함수, useState(0); 최초 0으로 초기화
    // const [count, setCount] = useState(0); 
  
    // function upClick(){
    //   setCount(count + 1);
    // }
    return(
      <button onClick={onClick} >
        {count} 번 증가!!
      </button>
    )
  }
  
  export default IncButton;
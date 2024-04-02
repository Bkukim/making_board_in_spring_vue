import axios from "axios";

export default axios.create({
    baseURL : "http://localhost:8000/api",// 관례적으로 공통url을 "api"로 많이 사용한다.
    headers : {
        "Content-Type": "application/json" // '문서의 종류는 json파일이다'라는 것을 알려주는 것이다.
    }// 문서 종류를 넣는 곳이다. 
}) // 다른 파일에서도 사용할 수 있게 해주는 것


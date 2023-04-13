import React, { useEffect, useState } from 'react';
import axios from 'axios';
import {Link, useNavigate} from 'react-router-dom';

function Main() {
    const [nickname, setNickname] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        axios
            .get('http://localhost:8080/member', {
                headers: {
                    Authorization: localStorage.getItem('Authorization'),
                },
            })
            .then(response => {
                if (response.data.accessToken != null) {
                    localStorage.setItem("Authorization", response.data.accessToken);
                }
                setNickname(response.data.nickname);
            })
            .catch(error => {
                navigate("/");
            });
    }, []);

    const handleLogout = () => {
        axios.post('http://localhost:8080/logout')
            .then(response => {
                localStorage.removeItem('Authorization');
                navigate("/");
            })
            .catch(error => {
                console.error(error);
            });
    };

    return (
        <div className="container-fluid">
            <div className="row">
                <div className="col-lg-8 mx-auto">
                    <div className="text-center py-5">
                        <h1 className="display-4">{nickname}님 반갑습니다!</h1>
                    </div>
                    <div className="text-center my-3">
                        <Link to="/setting" className="btn btn-lg btn-outline-primary">
                            SendQuiz 서비스 신청하기
                        </Link>
                        <button onClick={handleLogout} className="btn btn-lg btn-outline-secondary mx-2">
                            로그아웃
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Main;

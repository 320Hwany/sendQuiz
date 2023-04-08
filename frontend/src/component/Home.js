import React, {useEffect, useState} from 'react';
import {Link, useNavigate} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import axios from "axios";

function Home() {
    const navigate = useNavigate();

    useEffect(() => {
        axios
            .get('http://localhost:8080/member', {
                headers: {
                    Authorization: localStorage.getItem('Authorization'),
                },
            })
            .then(response => {
                navigate("/main");
            })
            .catch(error => {
            });
    });

    return (
        <div className="container my-5">
            <div className="row justify-content-center">
                <div className="col-md-6">
                    <div className="card">
                        <div className="card-body">
                            <h1 className="card-title text-center mb-4" style={{color: 'navy'}}>SendQuiz</h1>
                            <p className="card-text text-center">매일 아침 7시, 당신의 CS 면접을 대비해보세요.</p>
                            <p className="card-text text-center">SendQuiz는 원하는 분야의 CS 면접 질문 및 해답을 제공하는 서비스입니다.</p>
                            <p className="card-text text-center">원하는 분야와 문제 수를 설정하면, 매일 아침 메일로 문제를 받아보실 수 있습니다.</p>
                            <div className="text-center mt-4">
                                <Link to="/login" className="btn btn-primary mx-3">
                                    로그인
                                </Link>
                                <Link to="/signup" className="btn btn-secondary mx-3">
                                    회원가입
                                </Link>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Home;

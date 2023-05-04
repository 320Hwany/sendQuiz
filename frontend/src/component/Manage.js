import React, {useEffect, useState} from 'react';
import {Link, useNavigate} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import axios from "axios";

function Manage() {
    const navigate = useNavigate();

    useEffect(() => {
        axios
            .get('http://localhost:8080/member', {
                headers: {
                    Authorization: localStorage.getItem('Authorization'),
                },
            })
            .then(response => {
            })
            .catch(error => {
                navigate("/");
            });
    }, []);

    return (
        <div className="container my-5">
            <div className="row justify-content-center">
                <div className="col-md-6">
                    <div className="card">
                        <div className="card-body">
                            <h1 className="card-title text-center mb-4">회원정보 관리</h1>
                            <div className="d-flex justify-content-center mt-4">
                                <Link to="/update" className="btn btn-primary mx-3">
                                    회원정보 수정
                                </Link>
                                <Link to="/delete" className="btn btn-danger mx-3">
                                    회원 탈퇴
                                </Link>
                                <Link to="/main" className="btn btn-success mx-3">
                                    홈으로
                                </Link>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Manage;

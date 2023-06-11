import React, {useEffect, useState} from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Link, useNavigate} from "react-router-dom";

function Setting() {
    const navigate = useNavigate();

    useEffect(() => {
        axios
            .get('/api/member', {
                headers: {
                    Access_token: localStorage.getItem('Access_token'),
                },
                withCredentials: true,
            })
            .then(response => {
                if (response.data.accessToken != null) {
                    localStorage.setItem("Access_token", response.data.accessToken);
                }
            })
            .catch(error => {
                navigate("/");
            });
    }, []);

    const [options,
        setOptions] = useState({
        isNetwork: false,
        isDatabase: false,
        isOS: false,
        isDataStructure: false,
        isJava: false,
        isSpring: false,
        numOfProblem: 5
    });

    const handleOptionChange = (event) => {
        const { name, checked } = event.target;
        setOptions({
            ...options,
            [name]: checked,
        });
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await axios.post(
                '/api/quizFilter',
                options,
                {
                    headers: {
                        Access_token: localStorage.getItem('Access_token'),
                    },
                    withCredentials: true,
                }
            );
            navigate("/main");
        } catch (error) {
            console.error(error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <h2 className="text-center my-5">메일로 받고 싶은 분야를 골라주세요</h2>
            <div
                className="d-grid gap-2 my-5"
                style={{ maxWidth: '400px', margin: '0 auto' }}>
                <button
                    className={`btn ${
                        options.isNetwork ? 'btn-primary' : 'btn-outline-primary'
                    }`}
                    type="button"
                    onClick={() =>
                        handleOptionChange({
                            target: { name: 'isNetwork', checked: !options.isNetwork },
                        })
                    }>
                    네트워크
                </button>
                <button
                    className={`btn ${
                        options.isDatabase ? 'btn-primary' : 'btn-outline-primary'
                    }`}
                    type="button"
                    onClick={() =>
                        handleOptionChange({
                            target: { name: 'isDatabase', checked: !options.isDatabase },
                        })
                    }>
                    데이터베이스
                </button>
                <button
                    className={`btn ${
                        options.isOS ? 'btn-primary' : 'btn-outline-primary'
                    }`}
                    type="button"
                    onClick={() =>
                        handleOptionChange({
                            target: {
                                name: 'isOS',
                                checked: !options.isOS,
                            },
                        })
                    }>
                    운영체제
                </button>
                <button
                    className={`btn ${
                        options.isDataStructure ? 'btn-primary' : 'btn-outline-primary'
                    }`}
                    type="button"
                    onClick={() =>
                        handleOptionChange({
                            target: { name: 'isDataStructure', checked: !options.isDataStructure },
                        })
                    }>
                    자료구조
                </button>
                <button
                    className={`btn ${options.isJava ? 'btn-primary' : 'btn-outline-primary'}`}
                    type="button"
                    onClick={() =>
                        handleOptionChange({ target: { name: 'isJava', checked: !options.isJava } })
                    }>
                    자바
                </button>
                <button
                    className={`btn ${
                        options.isSpring ? 'btn-primary' : 'btn-outline-primary'
                    }`}
                    type="button"
                    onClick={() =>
                        handleOptionChange({ target: { name: 'isSpring', checked: !options.isSpring } })
                    }>
                    스프링
                </button>
                <div>
                <button
                    className={`btn ${options.numOfProblem === 3 ? 'btn-primary' : 'btn-outline-primary'}`}
                    type="button"
                    style={{ width: `${100 / 3}%` }}
                    onClick={() =>
                        setOptions({
                            ...options,
                            numOfProblem: 3,
                        })
                    }>
                    3문제
                </button>
                <button
                    className={`btn ${options.numOfProblem === 5 ? 'btn-primary' : 'btn-outline-primary'}`}
                    type="button"
                    style={{ width: `${100 / 3}%` }}
                    onClick={() =>
                        setOptions({
                            ...options,
                            numOfProblem: 5,
                        })
                    }>
                    5문제
                </button>
                <button
                    className={`btn ${options.numOfProblem === 7 ? 'btn-primary' : 'btn-outline-primary'}`}
                    type="button"
                    style={{ width: `${100 / 3}%` }}
                    onClick={() =>
                        setOptions({
                            ...options,
                            numOfProblem: 7,
                        })
                    }>
                    7문제
                </button>
                </div>
            </div>
            <div className="text-center">
                <button type="submit" className="btn btn-success mt-3 d-inline-block mx-auto">
                    서비스 신청하기
                </button>
                <Link to="/main" className="btn btn-secondary mx-3 mt-3 d-inline-block ml-3">
                    홈으로
                </Link>
            </div>
        </form>
    );
}

export default Setting

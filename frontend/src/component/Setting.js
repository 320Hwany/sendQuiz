import React, {useEffect, useState} from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Link, useNavigate} from "react-router-dom";

function Setting() {
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

    const [options,
        setOptions] = useState({
        network: false,
        database: false,
        operatingSystem: false,
        dataStructure: false,
        java: false,
        spring: false,
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
                'http://localhost:8080/quizFilter',
                options,
                {
                    headers: {
                        Authorization: localStorage.getItem('Authorization'),
                    },
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
                        options.network ? 'btn-primary' : 'btn-outline-primary'
                    }`}
                    type="button"
                    onClick={() =>
                        handleOptionChange({
                            target: { name: 'network', checked: !options.network },
                        })
                    }>
                    네트워크
                </button>
                <button
                    className={`btn ${
                        options.database ? 'btn-primary' : 'btn-outline-primary'
                    }`}
                    type="button"
                    onClick={() =>
                        handleOptionChange({
                            target: { name: 'database', checked: !options.database },
                        })
                    }>
                    데이터베이스
                </button>
                <button
                    className={`btn ${
                        options.operatingSystem ? 'btn-primary' : 'btn-outline-primary'
                    }`}
                    type="button"
                    onClick={() =>
                        handleOptionChange({
                            target: {
                                name: 'operatingSystem',
                                checked: !options.operatingSystem,
                            },
                        })
                    }>
                    운영체제
                </button>
                <button
                    className={`btn ${
                        options.dataStructure ? 'btn-primary' : 'btn-outline-primary'
                    }`}
                    type="button"
                    onClick={() =>
                        handleOptionChange({
                            target: { name: 'dataStructure', checked: !options.dataStructure },
                        })
                    }>
                    자료구조
                </button>
                <button
                    className={`btn ${options.java ? 'btn-primary' : 'btn-outline-primary'}`}
                    type="button"
                    onClick={() =>
                        handleOptionChange({ target: { name: 'java', checked: !options.java } })
                    }>
                    자바
                </button>
                <button
                    className={`btn ${
                        options.spring ? 'btn-primary' : 'btn-outline-primary'
                    }`}
                    type="button"
                    onClick={() =>
                        handleOptionChange({ target: { name: 'spring', checked: !options.spring } })
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

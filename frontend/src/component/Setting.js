import React, { useEffect, useState } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import {useNavigate} from 'react-router-dom';

function Setting() {
    const [nickname, setNickname] = useState('');
    const [options, setOptions] = useState({
        network: false,
        database: false,
        operatingSystem: false,
        dataStructure: false,
    });
    const navigate = useNavigate();

    useEffect(() => {
        axios
            .get('http://localhost:8080/member', {
                headers: {
                    Authorization: localStorage.getItem('Authorization'),
                },
            })
            .then(response => {
                setNickname(response.data.nickname);
            })
            .catch(error => {
                // navigate("/");
            });
    });

    const handleOptionChange = event => {
        const { name, checked } = event.target;
        setOptions({
            ...options,
            [name]: checked,
        });
    };

    return (
        <div>
            <h2 className="text-center my-5">메일로 받고 싶은 분야를 골라주세요</h2>
            <div className="d-grid gap-2 my-5" style={{ maxWidth: "400px", margin: "0 auto" }}>
                <button
                    className={`btn ${options.network ? 'btn-primary' : 'btn-outline-primary'}`}
                    type="button"
                    onClick={() => handleOptionChange({ target: { name: 'network', checked: !options.network } })}>
                    네트워크
                </button>
                <button
                    className={`btn ${options.database ? 'btn-primary' : 'btn-outline-primary'}`}
                    type="button"
                    onClick={() => handleOptionChange({ target: { name: 'database', checked: !options.database } })}>
                    데이터베이스
                </button>
                <button
                    className={`btn ${options.operatingSystem ? 'btn-primary' : 'btn-outline-primary'}`}
                    type="button"
                    onClick={() =>
                        handleOptionChange({ target: { name: 'operatingSystem', checked: !options.operatingSystem } })
                    }>
                    운영체제
                </button>
                <button
                    className={`btn ${options.dataStructure ? 'btn-primary' : 'btn-outline-primary'}`}
                    type="button"
                    onClick={() =>
                        handleOptionChange({ target: { name: 'dataStructure', checked: !options.dataStructure } })
                    }>
                    자료구조
                </button>
                <button type="submit" className="btn btn-success mt-3 text-center">
                    서비스 신청하기
                </button>
            </div>
        </div>
    );
}

export default Setting;

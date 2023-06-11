import React, { useEffect, useState } from 'react';
import axios from 'axios';
import {Link, useNavigate} from 'react-router-dom';
import {ListGroup, ListGroupItem} from "react-bootstrap";

function Main() {
    const [nickname, setNickname] = useState('');
    const [isNetwork, setIsNetwork] = useState(false);
    const [isDatabase, setIsDatabase] = useState(false);
    const [isOS, setIsOS] = useState(false);
    const [isDataStructure, setIsDataStructure] = useState(false);
    const [isJava, setIsJava] = useState(false);
    const [isSpring, setIsSpring] = useState(false);
    const [numOfProblem, setNumOfProblem] = useState(0);
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
                setNickname(response.data.nickname);
            })
            .catch(error => {
                navigate("/");
            });
    }, []);

    useEffect(() => {
        axios
            .get('/api/quizFilter', {
                headers: {
                    Access_token: localStorage.getItem('Access_token'),
                },
            })
            .then(response => {
                if (response.data.accessToken != null) {
                    localStorage.setItem("Access_token", response.data.accessToken);
                }
                setIsNetwork(response.data.isNetwork);
                setIsDatabase(response.data.isDatabase);
                setIsOS(response.data.isOS);
                setIsDataStructure(response.data.isDataStructure);
                setIsJava(response.data.isJava);
                setIsSpring(response.data.isSpring);
                setNumOfProblem(response.data.numOfProblem);
            })
            .catch(error => {
            });
    }, []);

    const handleLogout = () => {
        axios.post('/api/logout', null,{
            headers: {
                Access_token: localStorage.getItem('Access_token'),
            },
        })
            .then(response => {
                localStorage.removeItem('Access_token');
                navigate("/");
            })
            .catch(error => {
                alert("실패");
                console.error(error);
            });
    };

    return (
        <div className="container-fluid">
            <div className="row">
                <div className="col-lg-8 mx-auto">
                    <div className="text-center py-5">
                        <h1 className="display-4">{nickname}님 반갑습니다!</h1>
                        {isNetwork || isDatabase || isOS || isDataStructure || isJava || isSpring || numOfProblem !== 0 ? (
                            <>
                                <h2 className="my-3">현재 이용중인 서비스</h2>
                                <ListGroup>
                                    {isNetwork && <ListGroupItem>네트워크</ListGroupItem>}
                                    {isDatabase && <ListGroupItem>데이터베이스</ListGroupItem>}
                                    {isOS && <ListGroupItem>운영체제</ListGroupItem>}
                                    {isDataStructure && <ListGroupItem>자료구조</ListGroupItem>}
                                    {isJava && <ListGroupItem>자바</ListGroupItem>}
                                    {isSpring && <ListGroupItem>스프링</ListGroupItem>}
                                    {numOfProblem !== 0 && (
                                        <ListGroupItem>{numOfProblem}문제</ListGroupItem>
                                    )}
                                </ListGroup>
                            </>
                        ) : null}
                    </div>
                    <div className="d-flex flex-column justify-content-center my-2 d-md-grid">
                        <Link to="/setting" class="btn btn-lg btn-outline-success my-2">
                            SendQuiz 서비스 신청하기
                        </Link>
                        <Link to="/manage" class="btn btn-lg btn-outline-primary my-2">
                            회원정보 관리
                        </Link>
                        <button className="btn btn-lg btn-outline-secondary my-2" onClick={handleLogout}>
                            로그아웃
                        </button>
                        <Link to="/suggestions" class="btn btn-lg btn-outline-dark my-2">
                            소중한 의견을 들려주세요
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Main;
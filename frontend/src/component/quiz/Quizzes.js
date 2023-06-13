import React, { useState } from 'react';
import axios from 'axios';
import { Form, Button, Container } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'react-toastify/dist/ReactToastify.css';

function Quizzes() {
    const [quizzes, setQuizzes] = useState([]);
    const [isNetwork, setIsNetwork] = useState(false);
    const [isDatabase, setIsDatabase] = useState(false);
    const [isOS, setIsOS] = useState(false);
    const [isDataStructure, setIsDataStructure] = useState(false);
    const [isJava, setIsJava] = useState(false);
    const [isSpring, setIsSpring] = useState(false);
    const [page, setPage] = useState(false);
    const [totalPage, setTotalPage] = useState(false);

    const getQuizzes = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.get('/api/quiz', {
                params: {
                    isNetwork,
                    isDatabase,
                    isOS,
                    isDataStructure,
                    isJava,
                    isSpring,
                    page
                },
                headers: {
                    Access_token: localStorage.getItem('Access_token'),
                },
                withCredentials: true,
            });
            setQuizzes(response.data.quizzesPagingResponse);
            setTotalPage(response.data.totalPage);
        } catch (err) {
            console.log(err.response.data.message);
            alert('퀴즈를 가져오지 못하였습니다');
        }
    };

    const toggleButton = (state, setState) => {
        setState(!state);
    };

    const changePage = (newPage) => {
        setPage(newPage);
    };

    const renderPageButtons = () => {
        const buttons = [];
        for (let i = 1; i <= totalPage; i++) {
            buttons.push(
                <Button
                    key={i}
                    variant={i === page ? 'success' : 'secondary'}
                    onClick={() => changePage(i)}
                    disabled={i === page}>
                    {i}
                </Button>
            );
        }
        return buttons;
    };


    return (
        <Container>
            <Form onSubmit={getQuizzes}>
                <Button type="submit">퀴즈 리스트 보기</Button>
            </Form>

            <div>
                <Button
                    variant={isNetwork ? 'success' : 'secondary'}
                    onClick={() => toggleButton(isNetwork, setIsNetwork)}>
                    네트워크
                </Button>
                <Button
                    variant={isDatabase ? 'success' : 'secondary'}
                    onClick={() => toggleButton(isDatabase, setIsDatabase)}>
                    데이터베이스
                </Button>
                <Button
                    variant={isOS ? 'success' : 'secondary'}
                    onClick={() => toggleButton(isOS, setIsOS)}>
                    운영체제
                </Button>
                <Button
                    variant={isDataStructure ? 'success' : 'secondary'}
                    onClick={() => toggleButton(isDataStructure, setIsDataStructure)}>
                    자료구조
                </Button>
            </div>

            <div>
                <Button
                    variant={isJava ? 'success' : 'secondary'}
                    onClick={() => toggleButton(isJava, setIsJava)}>
                    자바
                </Button>
                <Button
                    variant={isSpring ? 'success' : 'secondary'}
                    onClick={() => toggleButton(isSpring, setIsSpring)}>
                    스프링
                </Button>
            </div>
            <div>
                {renderPageButtons()}
            </div>
            <ul>
                {quizzes.map((quiz) => (
                    <li key={quiz.number}>
                        <p>과목: {quiz.subject}</p>
                        <p>문제: {quiz.problem}</p>
                        <p>답: {quiz.answer}</p>
                    </li>
                ))}
            </ul>
        </Container>
);
}

export default Quizzes;

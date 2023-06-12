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
                },
                headers: {
                    Access_token: localStorage.getItem('Access_token'),
                },
                withCredentials: true,
            });
            setQuizzes(response.data);
            alert('저장에 성공했습니다');
        } catch (err) {
            console.log(err.response.data.message);
            alert('저장에 실패했습니다');
        }
    };

    const toggleButton = (state, setState) => {
        setState(!state);
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

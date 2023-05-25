import React, { useEffect, useState } from 'react';
import axios from 'axios';
import {Form, Button, Container} from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'react-toastify/dist/ReactToastify.css';
import {useNavigate} from "react-router-dom";

function QuizUpdate() {
    const [quizId, setQuizId] = useState('');
    const [subject, setSubject] = useState('');
    const [problem, setProblem] = useState('');
    const [answer, setAnswer] = useState('');
    const navigate = useNavigate();

    const updateQuiz = (e) => {
        e.preventDefault();
        axios
            .patch('https://send-quiz.store/api/quiz', { quizId, subject, problem, answer }, {
                headers: {
                    Access_token: localStorage.getItem('Access_token'),
                },
            })
            .then((res) => {
                alert('저장에 성공했습니다');
            })
            .catch((err) => {
                console.log(err.response.data.message);
                alert('저장에 실패했습니다');
            });
    };

    return (
        <Container className="d-flex justify-content-center align-items-center">
            <div className="col-md-5">
                <h1 className="mt-5 d-flex justify-content-center align-items-center">퀴즈 수정</h1>
                <Form className="my-3" onSubmit={updateQuiz}>
                    <Form.Group className="mb-3" controlId="formBasicQuizId">
                        <Form.Label>퀴즈 ID</Form.Label>
                        <Form.Control
                            type="quizId"
                            name="quizId"
                            value={quizId}
                            onChange={(e) => setQuizId(e.target.value)}
                            placeholder="퀴즈 ID를 입력하세요"
                        />
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formBasicSubject">
                        <Form.Label>과목</Form.Label>
                        <Form.Control
                            type="subject"
                            name="subject"
                            value={subject}
                            onChange={(e) => setSubject(e.target.value)}
                            placeholder="과목을 입력하세요"
                        />
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formBasicProblem">
                        <Form.Label>문제</Form.Label>
                        <Form.Control
                            type="problem"
                            name="problem"
                            value={problem}
                            onChange={(e) => setProblem(e.target.value)}
                            placeholder="문제를 입력하세요"
                        />
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formBasicAnswer">
                        <Form.Label>정답</Form.Label>
                        <Form.Control
                            as="textarea"
                            rows={15}
                            name="answer"
                            value={answer}
                            onChange={(e) => setAnswer(e.target.value)}
                            placeholder="정답을 입력하세요"
                        />
                    </Form.Group>

                    <Button variant="primary" type="submit">
                        수정하기
                    </Button>
                </Form>
            </div>
        </Container>
    );
}

export default QuizUpdate;

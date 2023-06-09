import React, { useEffect, useState } from 'react';
import axios from 'axios';
import {Form, Button, Container} from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'react-toastify/dist/ReactToastify.css';
import {useNavigate} from "react-router-dom";

function Quiz() {
    const [subject, setSubject] = useState('');
    const [problem, setProblem] = useState('');
    const [answer, setAnswer] = useState('');

    const saveQuiz = (e) => {
        e.preventDefault();
        axios
            .post('/api/quiz', { subject, problem, answer }, {
                headers: {
                    Access_token: localStorage.getItem('Access_token'),
                },
                withCredentials: true,
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
                <h1 className="mt-5 d-flex justify-content-center align-items-center">퀴즈 저장</h1>
                <Form className="my-3" onSubmit={saveQuiz}>
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
                        저장하기
                    </Button>
                </Form>
            </div>
        </Container>
    );
}

export default Quiz;

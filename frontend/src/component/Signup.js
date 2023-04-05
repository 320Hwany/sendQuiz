import React, { useState } from 'react';
import axios from 'axios';
import { Container, Form, Button } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

function Signup() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleSignUp = (e) => {
        e.preventDefault();
        axios
            .post('/signup', { email, password })
            .then((res) => {
                console.log(res.data);
                // 회원가입 성공 시 처리할 코드 작성
            })
            .catch((err) => {
                console.error(err);
                // 회원가입 실패 시 처리할 코드 작성
            });
    };

    return (
        <Container className="d-flex justify-content-center align-items-center">
            <div className="col-md-4">
                <h1 className="mt-5 d-flex justify-content-center align-items-center">회원가입</h1>
                <Form className="my-3" onSubmit={handleSignUp}>
                    <Form.Group className="mb-3" controlId="formBasicEmail">
                        <Form.Label>이메일</Form.Label>
                        <Form.Control
                            type="email"
                            name="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            placeholder="이메일을 입력하세요."
                        />
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formBasicPassword">
                        <Form.Label>비밀번호</Form.Label>
                        <Form.Control
                            type="password"
                            name="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="비밀번호를 입력하세요."
                        />
                    </Form.Group>

                    <Button variant="primary" type="submit">
                        회원가입
                    </Button>
                </Form>
            </div>
        </Container>
    );
}

export default Signup;

import React, { useState } from 'react';
import axios from 'axios';
import { Container, Form, Button } from 'react-bootstrap';
import {Link, useNavigate} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'react-toastify/dist/ReactToastify.css';

function Login() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const [loginError, setLoginEmailError] = useState('');

    const handleLogin = (e) => {
        e.preventDefault();
        axios
            .post('/api/login', { email, password })
            .then((res) => {
                localStorage.setItem("Authorization", res.data.accessToken);
                navigate('/main');
            })
            .catch((err) => {
                console.log(err.response.data.message)
                setLoginEmailError(err.response.data.message);
            });
    };

    return (
        <Container className="d-flex justify-content-center align-items-center">
            <div className="col-md-4">
                <h1 className="mt-5 d-flex justify-content-center align-items-center">로그인</h1>
                <Form className="my-3" onSubmit={handleLogin}>
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
                        {loginError && <div className="text-danger">{loginError}</div>}
                    </Form.Group>
                    <Button className="btn-success" type="submit">
                        로그인
                    </Button>
                    <Link to="/main" className="btn btn-primary mx-3">
                        홈으로
                    </Link>
                </Form>
            </div>
        </Container>
    );
}

export default Login;

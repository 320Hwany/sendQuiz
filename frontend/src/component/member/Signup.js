import React, { useState } from 'react';
import axios from 'axios';
import { Container, Form, Button } from 'react-bootstrap';
import {Link, useNavigate} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'react-toastify/dist/ReactToastify.css';

function Signup() {
    const [email, setEmail] = useState('');
    const [nickname, setNickname] = useState('');
    const [password, setPassword] = useState('');
    const [certificationNum, setCertificationNum] = useState('');
    const [certificationNumMessage, setCertificationNumMessage] = useState('');
    const navigate = useNavigate();

    const [emailError, setEmailError] = useState('');
    const [certificationNumError, setCertificationNumError] = useState('');
    const [nicknameError, setNicknameError] = useState('');
    const [passwordError, setPasswordError] = useState('');
    const [emailNotFound, setEmailNotFound] = useState('');
    const [isButtonDisabled, setIsButtonDisabled] = useState(false); // 버튼 비활성화 상태를 추적하는 상태 변수

    const handleSignUp = (e) => {
        e.preventDefault();
        axios
            .post('/api/signup', { email, certificationNum, nickname, password })
            .then((res) => {
                navigate('/signup/success');
            })
            .catch((err) => {
                setEmailError("");
                setNicknameError("");
                setPasswordError("");
                if (err.response.data.validation) {
                    setEmailError(err.response.data.validation.email);
                    setNicknameError(err.response.data.validation.nickname);
                    setPasswordError(err.response.data.validation.password);
                    setCertificationNumError(err.response.data.validation.certificationNum);
                }

                if (err.response.data.message) {
                    setCertificationNumError(err.response.data.message);
                }
            });
    };

    function AuthenticationEmail(e) {
        if (isButtonDisabled) return; // 버튼이 비활성화 상태인 경우 중복 클릭 방지
        setIsButtonDisabled(true); // 버튼을 비활성화 상태로 설정
        const params = new URLSearchParams();
        params.append('email', email);
        if (email == null || email === '') {
            setEmailError('이메일을 입력해주세요');
            return;
        }
        setEmailError('');
        setEmailNotFound('');
        setCertificationNumMessage('인증번호가 전송되었습니다');

        axios
            .post('/api/email/signup', params, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
            })
            .then((response) => {
                setTimeout(() => {
                    setIsButtonDisabled(false); // 일정 시간 후 버튼을 다시 활성화 상태로 설정
                }, 30000); // 30초
            })
            .catch((err) => {
                setEmailNotFound(err.response.data.message);
                setCertificationNumMessage('');
                setIsButtonDisabled(false); // 에러 발생 시 버튼을 다시 활성화 상태로 설정
            });
    }

    return (
        <Container className="d-flex justify-content-center align-items-center">
            <div className="col-md-5">
                <h1 className="mt-5 d-flex justify-content-center align-items-center">회원가입</h1>
                <Form className="my-3" onSubmit={handleSignUp}>
                    <Form.Group className="mb-3" controlId="formBasicEmail">
                        <Form.Label>이메일</Form.Label>
                        <Form.Control
                            type="email"
                            name="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            placeholder="이메일을 입력하세요"
                        />
                        {emailError && <div className="text-danger">{emailError}</div>}
                        <Button className="my-2" variant="primary" onClick={AuthenticationEmail}
                                disabled={isButtonDisabled}>
                            인증번호 받기
                        </Button>
                        {certificationNumMessage && <div className="text-success">{certificationNumMessage}</div>}
                        {emailNotFound && <div className="text-danger">{emailNotFound}</div>}
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formBasicVerificationCode">
                        <Form.Label>인증번호</Form.Label>
                        <Form.Control
                            type="text"
                            name="verificationCode"
                            value={certificationNum}
                            onChange={(e) => setCertificationNum(e.target.value)}
                            placeholder="이메일로 받은 인증번호를 입력하세요"
                        />
                        {certificationNumError && <div className="text-danger">{certificationNumError}</div>}
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formBasicNickname">
                        <Form.Label>닉네임</Form.Label>
                        <Form.Control
                            type="nickname"
                            name="nickname"
                            value={nickname}
                            onChange={(e) => setNickname(e.target.value)}
                            placeholder="닉네임을 입력하세요"
                        />
                        {nicknameError && <div className="text-danger">{nicknameError}</div>}
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formBasicPassword">
                        <Form.Label>비밀번호</Form.Label>
                        <Form.Control
                            type="password"
                            name="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="비밀번호를 입력하세요"
                        />
                        {passwordError && <div className="text-danger">{passwordError}</div>}
                    </Form.Group>

                    <Button className="btn-success" type="submit">
                        회원가입
                    </Button>
                    <Link to="/main" className="btn btn-primary mx-3">
                        홈으로
                    </Link>
                </Form>
            </div>
        </Container>
    );
}

export default Signup;
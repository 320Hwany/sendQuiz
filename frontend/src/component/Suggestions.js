import React, { useEffect, useState } from 'react';
import axios from 'axios';
import {Form, Button, Container, Alert} from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'react-toastify/dist/ReactToastify.css';
import {Link, useNavigate} from 'react-router-dom';

function Suggestions() {
    const [contents, setContents] = useState('');
    const navigate = useNavigate();
    const [showAlert, setShowAlert] = useState(false);

    useEffect(() => {
        axios
            .get('https://send-quiz.store/api/member', {
                headers: {
                    Authorization: localStorage.getItem('Authorization'),
                },
                withCredentials: true,
            })
            .then(response => {
                if (response.data.accessToken != null) {
                    localStorage.setItem("Authorization", response.data.accessToken);
                }
            })
            .catch(error => {
                navigate("/");
            });
    }, []);

    const saveSuggestions = (e) => {
        e.preventDefault();
        axios
            .post('https://send-quiz.store/api/suggestions', { contents }, {
                headers: {
                    Authorization: localStorage.getItem('Authorization'),
                },
                withCredentials: true,
            })
            .then((res) => {
                setShowAlert(true);
                setTimeout(() => {
                    navigate("/");
                }, 1700);
            })
            .catch((err) => {
                console.log(err.response.data.message);
                alert('저장에 실패했습니다');
            });
    };

    return (
        <Container className="d-flex justify-content-center align-items-center">
            <div className="col-md-5">
                <h1 className="mt-5 d-flex justify-content-center align-items-center">소중한 의견을 들려주세요</h1>
                <Form className="my-3" onSubmit={saveSuggestions}>
                    <Form.Group className="mb-3" controlId="formBasicContents">
                        <Form.Label></Form.Label>
                        <Form.Control
                            as="textarea"
                            rows={15}
                            name="contents"
                            value={contents}
                            onChange={(e) => setContents(e.target.value)}
                            placeholder="입력해주세요"
                        />
                    </Form.Group>

                    <div className="d-flex text-center mt-4">
                        <button type="button" className="btn btn-success"
                                onClick={saveSuggestions}>의견 보내기</button>
                        <Link to="/main" className="btn btn-primary mx-3">
                            홈으로
                        </Link>
                    </div>
                    <Alert variant="success" show={showAlert}
                           onClose={() => setShowAlert(false)} dismissible>
                        소중한 의견 감사합니다
                    </Alert>
                </Form>
            </div>
        </Container>
    );
}

export default Suggestions;

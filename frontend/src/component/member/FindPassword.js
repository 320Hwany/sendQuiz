import React, {useEffect, useState} from 'react';
import axios from "axios";
import {Link, useNavigate} from "react-router-dom";
import {Alert} from "react-bootstrap";

function FindPassword() {
    const navigate = useNavigate();

    const [email, setEmail] = useState('');
    const [notMatchError, setNotMatchError] = useState('');

    const handleEmailChange = (event) => {
        setEmail(event.target.value);
    };

    const [showAlert, setShowAlert] = useState(false);

    const handlePasswordFindClick = () => {
        const params = new URLSearchParams();
        params.append('email', email);
        if (email == null || email === "") {
            setNotMatchError("이메일을 입력해주세요");
            return;
        }
        setShowAlert(true);
        axios.post('https://send-quiz.store/api/email/password', params,{
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        })
            .then((response) => {
                setTimeout(() => {
                    navigate("/");
                });
            })
            .catch((error) => {
                setShowAlert(false);
                if (error.response.data.message) {
                    setNotMatchError(error.response.data.message);
                }
            });
    };

    return (
        <div className="container my-5">
            <div className="row justify-content-center">
                <div className="col-md-6">
                    <div className="card">
                        <div className="card-body">
                            <h1 className="card-title text-center mb-4">비밀번호 찾기</h1>
                            <form>
                                <div className="form-group">
                                    <label htmlFor="email">이메일</label>
                                    <input type="text" className="form-control" id="email" value={email}
                                           onChange={handleEmailChange} />
                                </div>

                                {notMatchError && (
                                    <div className="alert alert-danger custom-alert" role="alert">
                                        {notMatchError}
                                    </div>
                                )}

                                <div className="d-flex text-center mt-4">
                                    <button type="button" className="btn btn-danger"
                                            onClick={handlePasswordFindClick}>임시 비밀번호 전송</button>
                                    <Link to="/main" className="btn btn-primary mx-3">
                                        홈으로
                                    </Link>
                                </div>
                            </form>
                            <Alert variant="success" show={showAlert}
                                   onClose={() => setShowAlert(false)} dismissible>
                                임시 비밀번호가 전송되었습니다
                            </Alert>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default FindPassword;

import React, {useEffect, useState} from 'react';
import axios from "axios";
import {Link, useNavigate} from "react-router-dom";
import {Alert} from "react-bootstrap";

function Update() {
    const navigate = useNavigate();

    const [nickname, setNickname] = useState('');
    const [password, setPassword] = useState('');
    const [notMatchError, setNotMatchError] = useState('');

    useEffect(() => {
        axios
            .get('https://send-quiz.store/api/member', {
                headers: {
                    Access_token: localStorage.getItem('Access_token'),
                    Refresh_token: localStorage.getItem('Refresh_token'),
                },
            })
            .then(response => {
                if (response.data.accessToken != null) {
                    localStorage.setItem("Access_token", response.data.accessToken);
                }
            })
            .catch(error => {
                navigate("/");
            });
    }, []);

    const handleNicknameChange = (event) => {
        setNickname(event.target.value);
    };

    const handlePasswordCChange = (event) => {
        setPassword(event.target.value);
    };

    const [showAlert, setShowAlert] = useState(false);

    const handleUpdateClick = () => {
        axios.patch('https://send-quiz.store/api/member', { nickname, password }, {
            headers: {
                Access_token: localStorage.getItem('Access_token'),
                Refresh_token: localStorage.getItem('Refresh_token'),
            },
        })
            .then((response) => {
                setShowAlert(true);
                setTimeout(() => {
                    navigate("/");
                }, 1700);
            })
            .catch((error) => {
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
                            <h1 className="card-title text-center mb-4" style={{ color: 'navy' }}>회원 수정</h1>
                            <form>
                                <div className="form-group">
                                    <label htmlFor="nickname">닉네임</label>
                                    <input type="text" className="form-control" id="nickname" value={nickname}
                                           onChange={handleNicknameChange} />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="password">비밀번호</label>
                                    <input type="password" className="form-control" id="password" value={password}
                                           onChange={handlePasswordCChange} />
                                </div>

                                {notMatchError && (
                                    <div className="alert alert-danger custom-alert" role="alert">
                                        {notMatchError}
                                    </div>
                                )}

                                <div className="d-flex text-center mt-4">
                                    <button type="button" className="btn btn-danger"
                                            onClick={handleUpdateClick}>회원 수정</button>
                                    <Link to="/main" className="btn btn-success mx-3">
                                        홈으로
                                    </Link>
                                </div>
                            </form>
                            <Alert variant="success" show={showAlert}
                                   onClose={() => setShowAlert(false)} dismissible>
                                회원수정이 되었습니다.
                            </Alert>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Update;

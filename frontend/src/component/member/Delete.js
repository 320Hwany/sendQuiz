import React, {useEffect, useState} from 'react';
import axios from "axios";
import {Link, useNavigate} from "react-router-dom";
import {Alert} from "react-bootstrap";

function Delete() {
    const navigate = useNavigate();

    const [password, setPassword] = useState('');
    const [passwordCheck, setPasswordCheck] = useState('');
    const [notMatchError, setNotMatchError] = useState('');
    const [isButtonDisabled, setIsButtonDisabled] = useState(false); // 버튼 비활성화 상태를 추적하는 상태 변수

    useEffect(() => {
        axios
            .get('/api/member', {
                headers: {
                    Access_token: localStorage.getItem('Access_token'),
                },
                withCredentials: true,
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

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

    const handlePasswordCheckChange = (event) => {
        setPasswordCheck(event.target.value);
    };

    const [showAlert, setShowAlert] = useState(false);

    const handleDeleteClick = () => {
        if (isButtonDisabled) return; // 버튼이 비활성화 상태인 경우 중복 클릭 방지
        setIsButtonDisabled(true); // 버튼을 비활성화 상태로 설정
        axios.post('/api/withdrawal', { password, passwordCheck }, {
            headers: {
                Access_token: localStorage.getItem('Access_token'),
            },
            withCredentials: true,
        })
            .then((response) => {
                setShowAlert(true);
                setTimeout(() => {
                    setIsButtonDisabled(false); // 일정 시간 후 버튼을 다시 활성화 상태로 설정
                }, 30000); // 30초
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
                            <h1 className="card-title text-center mb-4" style={{ color: 'navy' }}>회원 탈퇴</h1>
                            <form>
                                <div className="form-group">
                                    <label htmlFor="password">비밀번호</label>
                                    <input type="password" className="form-control" id="password" value={password}
                                           onChange={handlePasswordChange} />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="passwordCheck">비밀번호 확인</label>
                                    <input type="password" className="form-control" id="passwordCheck" value={passwordCheck}
                                           onChange={handlePasswordCheckChange} />
                                </div>

                                {notMatchError && (
                                    <div className="alert alert-danger custom-alert" role="alert">
                                        {notMatchError}
                                    </div>
                                )}

                                <div className="d-flex text-center mt-4">
                                    <button type="button" className="btn btn-danger"
                                            onClick={handleDeleteClick} disabled={isButtonDisabled}>회원 탈퇴</button>
                                    <Link to="/main" className="btn btn-success mx-3">
                                        홈으로
                                    </Link>
                                </div>
                            </form>
                            <Alert variant="success" show={showAlert}
                                   onClose={() => setShowAlert(false)} dismissible>
                                회원탈퇴가 되었습니다.
                            </Alert>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Delete;

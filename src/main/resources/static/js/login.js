function login(userType) {
	// 로그인 버튼 ID 선택
	const $tabName = $('#' + userType + '-tab');

	//전부 비우고 갱신
	$('#admin-username-error').hide();
	$('#admin-password-error').hide();
	$('#admin-match-error').hide();
	$('#seller-username-error').hide();
	$('#seller-password-error').hide();
	$('#seller-match-error').hide();

	// 로그인 중인지 완료 상태인지 확인
	if ($tabName.hasClass('active')) {
		console.log(userType);

		// 로그인 폼의 사용자 이름과 비밀번호 입력 값 가져오기
		const $username = $('#' + userType + '-username');
		const $password = $('#' + userType + '-password');

		// 경고 메시지 요소 가져오기
		const $usernameError = $('#' + userType + '-username-error');
		const $passwordError = $('#' + userType + '-password-error');
		const $matchError = $('#' + userType + '-match-error');

		// 입력이 비어있으면 경고 메시지 표시
		let valid = true;

		if (!$username.val().trim()) {
			$usernameError.show();
			valid = false;
		} else {
			$usernameError.hide();
		}

		if (!$password.val().trim()) {
			$passwordError.show();
			valid = false;
		} else {
			$passwordError.hide();
		}

		// 입력이 모두 유효하면 로그인 처리
		if (valid) {	
			if ($username.val() === '1' && $password.val() === '1') {
				alert(userType + ' 로그인 성공');
			}else{
				$matchError.show();
			}
		} else {
			return;
		}
	}//active if문
}

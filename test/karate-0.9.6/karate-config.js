function fn() {
	var config = {
		"여기에 공통 환경 변수를 넣으세요":""
		,"baseUrl": "http://localhost:9090"
	};
	karate.configure('ssl', { trustAll: true });
	return config;
}

package com.say.popo.popoalbum.service;

import org.springframework.stereotype.Service;

import com.say.popo.popoalbum.entity.Post;
import com.say.popo.popoalbum.repository.PostRepository;

@Service
public class FirstMessageService {
	
	/*private final VisionService visionService = new VisionService();*/
	private final GeminiService geminiService = new GeminiService();
	private final PostRepository postrepository;
	
	public FirstMessageService(PostRepository postrepository) {
		this.postrepository = postrepository;
	}
	
	public String generateMessage(String fullUrl, String userComment) {
		
		//DBに保存
		Post post = new Post();
		post.setImage_url(fullUrl);
		post.setCaption(userComment);
		
		postrepository.save(post);
		/*	
		//①Visionでタグを抽出
		List<String> tags = visionService.analyzeImageByUrl(fullUrl); */
		
		//②プロンプト構築
		String prompt = buildPrompt(/*tags,*/userComment);
		//デバッグ用
		System.out.println("生成されたプロンプト：" + prompt);
		
		//③Geminiでコメント生成
		return geminiService.generateMessage(prompt);
	}
	
	private String buildPrompt(/*List<String> tags, */String comment) {
		return """
				投稿された画像には以下のタグが見つかりました：
				
				
				ユーザーコメント「%s」に対して、純粋で優しい男の子の子犬のポポが、親しみをこめたコメントをしてください。
				""".formatted(/*String.join(",", tags),*/comment);
	}

}

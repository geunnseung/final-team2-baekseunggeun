package com.likelion.sns.service;

import com.likelion.sns.repository.PostRepository;
import com.likelion.sns.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
}

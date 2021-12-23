package com.example.myapplication.di.chat

import com.example.myapplication.di.FragmentScope
import com.example.myapplication.di.main.ActivityComponent
import com.example.myapplication.presentation.chat.ChatViewModel
import dagger.Component

@FragmentScope
@Component(dependencies = [ActivityComponent::class])
interface ChatComponent {

    fun getChatViewModel(): ChatViewModel
}
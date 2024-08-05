package com.hackathon.devlabsvendor.ui.main.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hackathon.devlabsvendor.adapter.home.message.MessageAdapter
import com.hackathon.devlabsvendor.databinding.ActivityMessageBinding
import com.hackathon.devlabsvendor.model.AddMessageRequest
import com.hackathon.devlabsvendor.viewmodel.MessageViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MessageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessageBinding
    private val messageViewModel: MessageViewModel by viewModels()
    private lateinit var messageAdapter: MessageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val clientId = intent.getStringExtra("client_id")
        val userId = intent.getStringExtra("user_id")
        val token = intent.getStringExtra("token")
        messageAdapter = MessageAdapter(emptyList())
        binding.rvMessages.layoutManager = LinearLayoutManager(this)
        binding.rvMessages.adapter = messageAdapter

        messageViewModel.isLoading.observe(this, Observer { isLoading ->
            showLoading(isLoading)
        })

        messageViewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })

        messageViewModel.getMessage.observe(this, Observer { messages ->
            messageAdapter.updateMessages(messages.data)
            binding.rvMessages.scrollToPosition(messages.data.size - 1)
        })

        if (clientId != null && userId != null) {
            messageViewModel.getMessage(token!!, userId, clientId)
            messageViewModel.viewModelScope.launch {
                while (true) {
                    messageViewModel.startFetchingMessages(token, userId, clientId)
                    delay(10000) // Fetch new messages every 10 seconds
                }
            }

            binding.btnSend.setOnClickListener {
                val messageText = binding.etMessage.text.toString()
                if (messageText.isNotBlank()) {
                    val addMessageRequest = AddMessageRequest(receiverId = clientId, message = messageText)
                    messageViewModel.addMessage(token, addMessageRequest)
                } else {
                    Toast.makeText(this, "Message cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}
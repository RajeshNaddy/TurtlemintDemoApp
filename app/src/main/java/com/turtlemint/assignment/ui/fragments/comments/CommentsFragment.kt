package com.turtlemint.assignment.ui.fragments.comments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.transition.MaterialContainerTransform
import com.turtlemint.assignment.R
import com.turtlemint.assignment.data.model.Issues
import com.turtlemint.assignment.databinding.FragmentCommentsBinding
import com.turtlemint.assignment.ui.MainViewModel
import com.turtlemint.assignment.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentsFragment : Fragment(R.layout.fragment_comments) {

    private var _binding: FragmentCommentsBinding? = null
    private val binding get() = _binding!!
    private val commentsAdapter:CommentRecyclerAdapter by lazy(LazyThreadSafetyMode.NONE) { CommentRecyclerAdapter() }
    private val viewModel: MainViewModel by activityViewModels()
    private val args: CommentsFragmentArgs by navArgs()
    private val issue: Issues by lazy(LazyThreadSafetyMode.NONE) { args.issueData }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            duration = resources.getInteger(R.integer.motion_duration_long).toLong()
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(requireContext().themeColor(com.google.android.material.R.attr.colorSurface))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommentsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.fetchComments(issue.comments_url,issue.id)
        viewModel.commentList.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    binding.loader.makeVisible()
                }
                is DataState.Success -> {
                    binding.loader.makeGone()
                    if (dataState.data.isEmpty())
                        binding.tvNoComments.makeVisible()
                    else {
                        binding.tvNoComments.makeGone()
                        commentsAdapter.submitList(dataState.data)
                    }
                }
                is DataState.Error -> {
                    binding.loader.makeGone()
                    showToast("${dataState.exception.message}")
                }
            }

        }
    }

    private fun setupViews() {
        binding.apply {
            rvComments.adapter = commentsAdapter
            tvTitle.text = issue.title
            ivAvatar.load(issue.user.avatar_url) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            tvUsername.text = issue.user.login
            tvUpdatedOn.text = getString(R.string.label_issue_update,issue.updated_at.toFormattedDateString())
        }
    }

}
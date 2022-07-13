package com.turtlemint.assignment.ui.fragments.issues

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import com.turtlemint.assignment.R
import com.turtlemint.assignment.data.model.Issues
import com.turtlemint.assignment.databinding.FragmentHomeBinding
import com.turtlemint.assignment.ui.MainViewModel
import com.turtlemint.assignment.util.DataState
import com.turtlemint.assignment.util.makeGone
import com.turtlemint.assignment.util.makeVisible
import com.turtlemint.assignment.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IssuesFragment : Fragment(R.layout.fragment_home), IssuesInteraction {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var issuesAdapter: IssuesRecyclerAdapter? = null
    private val viewModel: MainViewModel by activityViewModels()
    private var onStart = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough().apply {
            duration = resources.getInteger(R.integer.motion_duration_long).toLong()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        setupViews()
        setupObservers()
    }

    private fun setupViews() {
        issuesAdapter = IssuesRecyclerAdapter(this)
        binding.rvIssues.apply {
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayout.VERTICAL))
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = issuesAdapter
        }
    }

    private fun setupObservers() {
        viewModel.fetchIssues()
        viewModel.issueList.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    binding.loader.makeVisible()
                }
                is DataState.Success -> {
                    binding.loader.makeGone()
                    issuesAdapter?.submitList(dataState.data) {
                        binding.rvIssues.scrollToPosition(0)
                    }
                }
                is DataState.Error -> {
                    binding.loader.makeGone()
                    showToast("${dataState.exception.message}")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onIssueClicked(view: View, issue: Issues) {
        exitTransition = MaterialElevationScale(false).apply {
            duration = resources.getInteger(R.integer.motion_duration_long).toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.motion_duration_long).toLong()
        }

        val transitionName = getString(R.string.transition_name_issue_comment)
        val extras = FragmentNavigatorExtras(view to transitionName)
        val directions = IssuesFragmentDirections.actionHomeFragmentToCommentsFragment(issue)
        findNavController().navigate(directions, extras)
    }

}
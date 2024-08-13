//
//  HomeScreen.swift
//  iosApp
//
//  Created by Adrià Fernández Arans on 13/8/24.
//  Copyright © 2024 orgName. All rights reserved.
//

extension Movie: Identifiable { }

import SwiftUI
import ComposeApp

struct HomeScreen: View {
    var viewModel = HomeViewModel()
    
    var body: some View {
        Observing(viewModel.state){ state in
            if (state.isLoading) {
                ProgressView()
                    .progressViewStyle(CircularProgressViewStyle())
            }
            if (!state.movies.isEmpty) {
                let columns = [GridItem(.adaptive(minimum: 100))]
                
                ScrollView{
                    LazyVGrid(columns: columns){
                        ForEach(state.movies) { movie in
                            Text(movie.title)
                        }
                    }
                }
            }
        }.onAppear {
            viewModel.onEvent(event: HomeViewModel.UiEventOnUiReady())
        }
    }
}

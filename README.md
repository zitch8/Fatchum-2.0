# Fatchum 2.0
**Fatchum 2.0** is a recipe recommendation app built with Kotlin (Android) and a FastAPI backend. It helps users discover recipes using ingredients they already have. This app provide food recommendations using TF-IDF and cosine similarity.

## Features
**Mobile App (Kotlin)**
- Search recipes by typing ingredients you have in your fridge.
- Get suggestions by food tags
- Firebase integration that supports Google Accounts

**Backend (FastAPI + ML)**
-**Count Vectorizer + Cosine Similarity** -> tag-based recommendations.
-**TF-IDF + Cosine Similarity** -> ingredient-based search engine.

** Tech Stack**

**Mobile (Frontend)**
- Kotlin + Andorid Studio
- Firebase (Auth, Firestore, Cloud Storage)
- Retrofit for API calls

**Backend (API)**
- FastAPI
- Scikit-learn (TF-IDF, CountVectorizer, Cosine Similarity)
- Uvicorn / Gunicorn for deployment
- Render for server hosting
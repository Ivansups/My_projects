#include <exception>
#include <fstream>
#include <iostream> 
#include <string>
using namespace std;

int main() {
    const int MAX_WORDS = 1000;
    string words[MAX_WORDS];
    int counts[MAX_WORDS];
    int wordCount = 0;
    
    ifstream intputFile;
    ofstream outFile;
    string word;

    try {
        intputFile.open("f1.txt");
        if (!intputFile.is_open()) {
            cout << "Не удалось открыть файл" << endl;
            return 1;
        }
    } catch (exception e) {
        throw e;
    }

    while (intputFile >> word && wordCount < MAX_WORDS) {
        for (int i = 0; i < word.length(); i++) {
            word[i] = tolower(word[i]);
        }
        
        bool found = false;
        for (int i = 0; i < wordCount; i++) {
            if (words[i] == word) {
                counts[i]++;
                found = true;
                break;
            }
        }
        
        if (!found) {
            words[wordCount] = word;
            counts[wordCount] = 1;
            wordCount++;
        }
    }
    intputFile.close();

    try{
        outFile.open("f2.txt");
        if (!outFile.is_open()) {
            cout << "Не удалось открыть файл для записи" << endl;
            return 1;
        }
    } catch (exception e) {
        throw e;
    }

    for (int i = 0; i < wordCount; i++) {
        outFile << words[i] << ": " << counts[i] << endl;
    }
    outFile.close();

    return 0;
}
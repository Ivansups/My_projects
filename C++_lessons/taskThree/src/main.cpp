#include <fstream>
#include <iostream> 
#include <unordered_map>
#include <string>
#include <algorithm>
using namespace std;
int main() {
    unordered_map<std::string, int> wordCount;
    ifstream file;
    ofstream outFile;
    string word;

    try {
        file = ifstream("f1.txt");
        if (!file.is_open()) {
            throw exception();
        }
    } catch (exception e) {
        cout << "Не удалось открыть файл" << endl;
        return 1;
    }

    while (file >> word) {
            transform(word.begin(), word.end(), word.begin(), ::tolower);
            if (wordCount.find(word) != wordCount.end()) {
                wordCount[word] += 1;
            } else {
                wordCount[word] = 1;
            }
        }
    file.close();

    try {
        outFile = ofstream("f2.txt");
        if (!outFile.is_open()) {
            throw exception();
        }
    } catch (exception e) {
        cout << "Не удалось открыть файл" << endl;
        return 1;
    }

    for (const auto& pair : wordCount) {
        outFile << pair.first << ": " << pair.second << std::endl;
    }
    outFile.close();

    return 0;
}
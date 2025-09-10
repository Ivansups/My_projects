#include <iostream>
#include <cmath>
using namespace std;
#include <iostream>
#include <cmath>
using namespace std;

int main() {
    while (true) {
        try {
            double x;
            cout << "Введите x: ";
            cin >> x;

            double sum = 0;
            int n = 1;
            double cur_term;
            double epsilon = 1e-6;
            
            do {
                cur_term = (pow(-1, n - 1) * pow(x + 2, n)) / ((3 * n - 1) * pow(5, n));
                sum += cur_term;
                n++;
            } while (abs(cur_term) > epsilon && n < 1000);
            
            cout << "Сумма ряда: " << sum << endl;
            cout << "Количество итераций: " << n - 1 << endl;
        } catch (exception e) {
            cout << "Ошибка: " << e.what() << endl;
        }
        break;
    }

    return 0;
}

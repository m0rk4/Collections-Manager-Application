import Vue from 'vue'
import VueI18n from "vue-i18n"

Vue.use(VueI18n)

export const i18n = new VueI18n({
    locale: 'en',
    fallbackLocale: 'ru',
    messages: {
        en: {
            appTitle: 'Reviewer',
            appDescription: '',
            searchPlaceholder: 'Search',
            login: 'Login',
            register: 'Register',
            username: 'Login',
            password: 'Password | password',
            or: 'or',
            enter: 'Enter',
            socialMedia: 'Enter via social media:',
            repeat: 'Repeat',
            tags: 'Tags',
            lastAddedItems: 'Last added items',
            collection: 'Collection',
            popCollections: 'The largest collections',
            popCollectionsDesc: 'Collections with the maximum items',
            update: 'Update',
            viewCollection: 'View collection',
            add: 'Add',
            comments: 'Comments',
            newComment: 'New comment',
            newCommentPlaceHolder: 'Type text...',
            isRequired: 'is required',
            loginRules: 'Login must be less than 15 characters and bigger than 3 characters',
            passRules: 'Password must be less than 15 characters and bigger than 5 characters',
            diffPass: 'Different passwords',
            searchResults: 'Search results',
            item: 'Item',
            resultsFor: 'Results for',
            sorryPageNF: 'Sorry, page not found',
            mainPage: 'Main page',
            userActivity: 'User activity',
            last5Days: 'Last 5 days contribution',
            regDate: 'Registration date',
            unknown: 'Unknown',
            admin: 'Admin',
            userColls: 'User collections',
            theme: 'Theme',
            delete: 'Delete',
            descMD: 'Description (MarkDown supported)',
            title: 'Title',
            upPic: 'Upload picture',
            status: 'Status',
            fields: 'Fields',
            creatUrField: 'Create your field',
            selFields: 'Select fields',
            fieldName: 'Field name',
            addField: 'Add field',
            submit: 'Submit',
            chOrAdd1F: 'Choose or Add at least one field',
            fileStatusInit: 'No file',
            titIsReq: 'Title is required',
            ch1Th: 'Choose one theme',
            chTFrList: 'Choose theme from the list',
            f15: 'Field name must be less than 15 characters',
            fNIsReq: 'Field name is required',
            upTo1000: 'Up to 1000 characters',
            description: 'Description',
            titleLess50: 'Title must be less than 50 characters',
            author: 'Author',
            itemsPP: 'Items per page',
            page: 'Page',
            of: 'of',
            sortBy: 'Sort by',
            cancel: 'Cancel',
            props: 'Properties',
            required: 'Required',
            tagIsReq: 'Tag is required',
            characters: 'characters',
            lenLess: 'Length must be less than',
        },
        ru: {
            appTitle: 'Ревьювер',
            appDescription: '',
            searchPlaceholder: 'Найти',
            login: 'Войти',
            register: 'Зарегистрироваться',
            username: 'Логин',
            password: 'Пароль | пароль',
            or: 'или',
            enter: 'Вход',
            socialMedia: 'Войти через соцсети:',
            repeat: 'Повторите',
            tags: 'Тэги',
            lastAddedItems: 'Последние добавленные предметы',
            collection: 'Коллекция',
            popCollections: 'Самые большие коллекции',
            popCollectionsDesc: 'Коллекции с наибольшим числом предметов',
            update: 'Редактировать',
            viewCollection: 'Перейти к коллекции',
            add: 'Добавить',
            comments: 'Комментарии',
            newComment: 'Новый комментарий',
            newCommentPlaceHolder: 'Введите текст...',
            isRequired: 'необходим',
            loginRules: 'Логин должен быть меньше 15 символов и больше 3 символов',
            passRules: 'Пароль должен быть меньше 15 символов и больше 5 символов',
            diffPass: 'Пароли не совпадают',
            searchResults: 'Результаты поиска',
            item: 'Предмет',
            resultsFor: 'Результаты по запросу',
            sorryPageNF: 'Извините, но страница не найдена',
            mainPage: 'Главная страница',
            userActivity: 'Активность пользователя',
            last5Days: 'Вклад за последние 5 дней',
            regDate: 'Дата регистрации',
            unknown: 'Неизвестно',
            admin: 'Админ',
            userColls: 'Коллекции пользователя',
            theme: 'Тема',
            delete: 'Удалить',
            descMD: 'Описание (MarkDown поддерживается)',
            title: 'Название',
            upPic: 'Загрузить картинку',
            status: 'Статус',
            fields: 'Поля',
            creatUrField: 'Создай своё поле',
            selFields: 'Выберите поля',
            fieldName: 'Название поля',
            addField: 'Добавить поле',
            submit: 'Подтвердить',
            chOrAdd1F: 'Выберите или добавьте хотя бы одно поле',
            fileStatusInit: 'Нет файла',
            titIsReq: 'Название необходимо',
            ch1Th: 'Выберите одну тему',
            chTFrList: 'Выберите тему из списка',
            f15: 'Имя поля должно быть не более 15 символов',
            fNIsReq: 'Имя поля необходимо',
            upTo1000: 'До 1000 символов',
            description: 'Описание',
            titleLess50: 'Название должно быть не более 50 символов',
            author: 'Автор',
            itemsPP: 'Предметов на странице',
            page: 'Страница',
            of: 'из',
            sortBy: 'Сортировать по',
            cancel: 'Отменить',
            props: 'Свойства',
            required: 'Необходимо',
            tagIsReq: 'Тэг необходим',
            characters: 'символов',
            lenLess: 'Длина должна быть меньше',
        }
    }
})
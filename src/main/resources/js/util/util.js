export function extractNewTags(idTags, unsignedTags) {
    let newTags = []
    unsignedTags.filter(t => !t.id).forEach(uT => {
        idTags.forEach(iT => {
            if (iT.name === uT.name) {
                newTags.push(iT)
            }
        })
    })
    return newTags
}

export function getIndex(targetCollection, item) {
    return targetCollection.findIndex(i => i.id === item.id)
}

export function getUpdatedCollection(targetCollection, item, index) {
    return index > -1 ? [...targetCollection.slice(0, index),
        item,
        ...targetCollection.slice(index + 1)] : targetCollection
}

export function deleteItemFromCollection(targetCollection, index) {
    return index > -1 ? [...targetCollection.slice(0, index),
        ...targetCollection.slice(index + 1)] : targetCollection
}

export function addComment(targetCollection, comment) {
    const indexToUpdate = getIndex(targetCollection, comment.item)
    if (indexToUpdate < 0) {
        return targetCollection
    }
    const item = targetCollection[indexToUpdate]

    if (!item.comments.find(it => it.id === comment.id)) {
        return getUpdatedCollection(
            targetCollection,
            {
                ...item,
                comments: [
                    ...item.comments,
                    comment
                ]
            },
            indexToUpdate)
    } else {
        return targetCollection
    }
}
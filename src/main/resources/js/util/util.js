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
    return [
        ...targetCollection.slice(0, index),
        item,
        ...targetCollection.slice(index + 1)
    ]
}

export function deleteItemFromCollection(targetCollection, index) {
    return [
        ...targetCollection.slice(0, index),
        ...targetCollection.slice(index + 1)
    ]
}